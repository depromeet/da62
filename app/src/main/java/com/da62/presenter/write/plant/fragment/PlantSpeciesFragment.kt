package com.da62.presenter.write.plant.fragment

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.da62.R
import com.da62.databinding.FragmentPlantRegistSpeciesBinding
import com.da62.databinding.ListPlantSpeciesSearchItemBinding
import com.da62.presenter.base.BaseFragment
import com.da62.presenter.write.plant.PlantRegistViewModel
import com.da62.util.toEditable
import org.jetbrains.anko.sdk27.coroutines.onFocusChange
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class PlantSpeciesFragment : BaseFragment<FragmentPlantRegistSpeciesBinding>() {

    val viewModel by sharedViewModel<PlantRegistViewModel>()

    override val resourceId: Int = R.layout.fragment_plant_regist_species

    private lateinit var searchResultAdapter: SearchResultAdapter

    private val timer = Timer()

    private var timerTask: TimerTask? = null

    private val speciesIllustAsset = listOf(
        Triple(R.drawable.ic_illust_1, R.drawable.ic_illust_1_trans, Card.FLOWER),
        Triple(R.drawable.ic_illust_2, R.drawable.ic_illust_2_trans, Card.TREE),
        Triple(R.drawable.ic_illust_3, R.drawable.ic_illust_3_trans, Card.DRUPE),
        Triple(R.drawable.ic_illust_4, R.drawable.ic_illust_4_trans, Card.GRASS),
        Triple(R.drawable.ic_illust_5, R.drawable.ic_illust_5_trans, Card.LEAF)
    )

    companion object {
        fun createInstance() =
            PlantSpeciesFragment().apply {

            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.viewModel = viewModel
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        speciesImageObserve()
        speciesObserve()
        searchResultObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timerTask?.let { task ->
            task.cancel()
            timerTask = null
        }
    }

    private fun init() {
        createSelectorAssets().run {
            binding.cbContainer.children.toList().filter { it is CheckBox }.forEach { view ->
                val item = poll()
                view.background = item.first
                view.tag = item.second
            }
        }
        binding.searchResultList.apply {
            layoutManager = LinearLayoutManager(context)
            searchResultAdapter = SearchResultAdapter { word ->
                word?.let {
                    binding.editText.text = it.toEditable()
                }
                searchResultAdapter.clear()
            }.apply {
                adapter = this
            }
        }
    }

    private fun createSelectorAssets() =
        context?.let { context ->
            LinkedList<Pair<StateListDrawable, Card>>().run {
                speciesIllustAsset.forEach {
                    offer(Pair(createSelectorAsset(context, it.first, it.second), it.third))
                }
                this
            }
        } ?: throw IllegalStateException()

    private fun createSelectorAsset(context: Context, @DrawableRes checkedResId: Int, @DrawableRes unCheckedResId: Int) =
        StateListDrawable().apply {
            addState(IntArray(1) { android.R.attr.state_checked }, ContextCompat.getDrawable(context, checkedResId))
            addState(IntArray(0), ContextCompat.getDrawable(context, unCheckedResId))
        }

    private fun speciesImageObserve() = viewModel.speciesImage.observe(this, Observer { card ->
        binding.cbContainer.children.toList()
            .filter { it.tag != card }
            .filter { it is CheckBox }
            .forEach { (it as CheckBox).isChecked = false }
    })

    private fun speciesObserve() = viewModel.species.observe(this, Observer {
        timerTask?.let { task ->
            task.cancel()
            timerTask = null
        }
        timerTask = SearchTimerTask(it) { word ->
            viewModel.inputSpeciesText(word)
        }.also { task ->
            timer.schedule(task, 1000)
        }
    })

    private fun searchResultObserve() = viewModel.searchResult.observe(this, Observer {
        searchResultAdapter.setItem(it)
    })
}

class SearchTimerTask(
    private val word: String,
    private val callback: (String) -> Unit
) : TimerTask() {

    override fun run() {
        callback.invoke(word)
    }
}

class SearchResultAdapter(private val click: (String?) -> Unit) :
    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {

    private val searchList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ListPlantSpeciesSearchItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false), click
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchList[position])
    }

    override fun getItemCount() = searchList.size

    fun setItem(searchList: List<String>) {
        this.searchList.clear()
        if (searchList.isNotEmpty()) {
            this.searchList.addAll(searchList)
        } else {
            this.searchList.add("결과없음")
        }
        notifyDataSetChanged()
    }

    fun clear() {
        searchList.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ListPlantSpeciesSearchItemBinding,
        private val click: (String?) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.result.text = text
            if (text == "결과없음") {
                binding.noSearch.visibility = View.VISIBLE
                itemView.setOnClickListener {
                    click(null)
                }
            } else {
                binding.noSearch.visibility = View.GONE
                itemView.setOnClickListener {
                    click(text)
                }
            }
        }
    }
}

enum class Card {
    FLOWER, DRUPE, GRASS, TREE, LEAF
}