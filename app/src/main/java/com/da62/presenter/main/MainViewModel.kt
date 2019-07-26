package com.da62.presenter.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.da62.model.ListType
import com.da62.model.Plant
import com.da62.presenter.base.BaseViewModel
import com.da62.usecase.MainUseCase
import com.da62.util.SingleLiveEvent
import com.da62.util.add
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(
    private val useCase: MainUseCase
) : BaseViewModel() {

    private var viewType = ListType.LIST

    private val _plantList = MutableLiveData<List<Plant>>()
    val plantList: LiveData<List<Plant>>
        get() = _plantList

    private val _clickToViewType = MutableLiveData<ListType>()
    val clickToViewType: LiveData<ListType>
        get() = _clickToViewType

    private val _isAddVisible = MediatorLiveData<Boolean>()
    val isAddVisible: LiveData<Boolean>
        get() = _isAddVisible

    private val _clickToAdd = SingleLiveEvent<Any>()
    val clickToAdd: LiveData<Any>
        get() = _clickToAdd

    private val _visibleProgress = MutableLiveData<Boolean>()
    val visibleProgress: LiveData<Boolean>
        get() = _visibleProgress

    private val _errorMessage = SingleLiveEvent<Any>()
    val errorMessage: LiveData<Any>
        get() = _errorMessage

    private val _refreshPosition = SingleLiveEvent<Int>()
    val refreshPosition: LiveData<Int>
        get() = _refreshPosition

    init {

        loadData()
        _isAddVisible.value = true

        _isAddVisible.addSource(_clickToViewType) {
            _isAddVisible.value = it == ListType.LIST
        }
    }

    fun loadData() {
        compositeDisposable add useCase.getPlantList()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _visibleProgress.postValue(true) }
            .subscribe({
                _plantList.value = it
                _visibleProgress.value = false
            }, {
                _errorMessage.postValue(false)
                _visibleProgress.postValue(false)
            })
    }

    fun clickToViewType() {
        _clickToViewType.value = changeViewType()
    }

    private fun changeViewType(): ListType {
        viewType = when (viewType) {
            ListType.LIST -> ListType.GRID
            else -> ListType.LIST
        }
        return viewType
    }

    fun clickToAdd() {
        _clickToAdd.call()
    }

    fun clickToLove(id: Int, position: Int) {
        compositeDisposable add useCase.postLove(id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _visibleProgress.postValue(true) }
            .map { it.love }
            .subscribe({ love ->
                _plantList.value?.let {
                    val result = it.toMutableList()
                    result[position] = result[position].copy(love = love)
                    _plantList.value = result
                }
                _refreshPosition.value = position
                _visibleProgress.value = false
            }, {
                _errorMessage.postValue(false)
                _visibleProgress.postValue(false)
            })
    }
}