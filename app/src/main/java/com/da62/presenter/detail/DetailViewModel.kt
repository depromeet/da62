package com.da62.presenter.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.da62.model.Plant
import com.da62.model.PlantInfo
import com.da62.presenter.base.BaseViewModel
import com.da62.usecase.DetailUseCase
import com.da62.util.SingleLiveEvent
import com.da62.util.add
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

class DetailViewModel(private val useCase: DetailUseCase) : BaseViewModel() {

    private var plantId: Int = 0

    private val _plantInfo = MutableLiveData<PlantInfo>()
    val plantInfo: LiveData<PlantInfo>
        get() = _plantInfo

    private val _clickToBack = SingleLiveEvent<Any>()
    val clickToBack: LiveData<Any>
        get() = _clickToBack

    private val _plant = MutableLiveData<Plant>()
    val plant: LiveData<Plant>
        get() = _plant

    private val _clickToGallery = SingleLiveEvent<Int>()
    val clickToGallery: LiveData<Int>
        get() = _clickToGallery

    private val _card = MutableLiveData<String>()
    val card: LiveData<String>
        get() = _card

    private val _visibleProgress = MutableLiveData<Boolean>()
    val visibleProgress: LiveData<Boolean>
        get() = _visibleProgress

    val raiseDate: LiveData<String> = Transformations.map(plant) {
        val diff = it.waterTime.time - Date().time
        val diffDays = diff / (24 * 60 * 60 * 1000)
        "${diffDays}일후"
    }

    val lovePercent: LiveData<String> = Transformations.map(plant) {
        val percent = ((it.love.toFloat() / 7f) * 100f)
        "${percent.toInt()}%"
    }

    private val _clickToWater = SingleLiveEvent<String>()
    val clickToWater: LiveData<String>
        get() = _clickToWater

    private val _clickToLove = SingleLiveEvent<Any>()
    val clickToLove: LiveData<Any>
        get() = _clickToLove

    private val _errorMessage = SingleLiveEvent<Any>()
    val errorMessage: LiveData<Any>
        get() = _errorMessage

    private val _clickToTrash = SingleLiveEvent<Any>()
    val clickToTrash: LiveData<Any>
        get() = _clickToTrash

    private val _deleteSuccess = SingleLiveEvent<Any>()
    val deleteSuccess: LiveData<Any>
        get() = _deleteSuccess

    fun clickToBack() {
        _clickToBack.call()
    }

    fun clickToWater() {
        _clickToWater.value = _plant.value?.name
    }

    fun clickToLove() {
        compositeDisposable add useCase.postLove(plantId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _visibleProgress.postValue(true) }
            .map { it.love }
            .subscribe({ love ->
                _plant.value?.let {
                    _plant.value = it.copy(love = love)
                }
                _visibleProgress.value = false
            }, {
                _errorMessage.call()
                _visibleProgress.postValue(false)
            })
    }

    fun clickToTrash() {
        _clickToTrash.call()
    }

    fun deletePlant() {
        compositeDisposable add useCase.delete(plantId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _visibleProgress.value = false
                _deleteSuccess.call()
            }, {
                _errorMessage.call()
                _visibleProgress.postValue(false)
            })
    }

    fun loadDetail(id: Int) {
        compositeDisposable add useCase.getDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _visibleProgress.postValue(true) }
            .subscribe({
                plantId = id
                updateUI(it)
                _visibleProgress.value = false
            }, {
                _errorMessage.call()
                _visibleProgress.postValue(false)
                it.printStackTrace()
            })
    }

    fun clickToGallery() {
        _clickToGallery.value = plantId
    }

    private fun updateUI(plant: Plant) {
        _plant.value = plant
        _plantInfo.value = plant.plantInfo
    }

    fun configureThumbnail(card: String) {
        _card.value = card
    }
}