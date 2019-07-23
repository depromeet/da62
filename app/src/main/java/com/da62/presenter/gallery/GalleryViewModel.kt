package com.da62.presenter.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.da62.model.PlantImageRequest
import com.da62.presenter.base.BaseViewModel
import com.da62.usecase.GalleryUseCase
import com.da62.util.SingleLiveEvent
import com.da62.util.add
import com.da62.util.toDateString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

class GalleryViewModel(private val useCase: GalleryUseCase) : BaseViewModel() {

    private var plantId: Int = 0

    private val _openAlbum = SingleLiveEvent<Any>()
    val openAlbum: LiveData<Any>
        get() = _openAlbum

    private val _clickToBack = SingleLiveEvent<Any>()
    val clickToBack: LiveData<Any>
        get() = _clickToBack

    private val _date = MutableLiveData<Date>()
    val date: LiveData<String> = Transformations.map(_date) {
        it.toDateString()
    }

    private val _thumbNail = MutableLiveData<String>()
    val thumbNail: LiveData<String>
        get() = _thumbNail

    val deleteButtonVisible: LiveData<Boolean> =
        Transformations.map(thumbNail) {
            !it.isNullOrEmpty()
        }

    private val _growType = MutableLiveData<String>()
    val growType: LiveData<String>
        get() = _growType

    private val _showToast = SingleLiveEvent<String>()
    val showToast: LiveData<String>
        get() = _showToast

    private val _visibleProgress = MutableLiveData<Boolean>()
    val visibleProgress: LiveData<Boolean>
        get() = _visibleProgress

    private val _uploadComplete = SingleLiveEvent<Any>()
    val uploadComplete: LiveData<Any>
        get() = _uploadComplete

    private val uploadSubject = PublishSubject.create<PlantImageRequest>()

    init {

        compositeDisposable add uploadSubject.throttleFirst(
            300,
            TimeUnit.MICROSECONDS,
            AndroidSchedulers.mainThread()
        )
            .doOnSubscribe { _visibleProgress.postValue(true) }
            .flatMapSingle { useCase.uploadImage(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _visibleProgress.value = false
                _showToast.value = "업로드 성공!"
                _uploadComplete.call()
            }, {
                _visibleProgress.postValue(false)
                _showToast.postValue("오류가 발생했습니다.")
                it.printStackTrace()
            })
    }

    fun configureGallery(plantId: Int) {
        this.plantId = plantId
    }

    fun openAlbum() {
        _openAlbum.call()
    }

    fun clickToBack() {
        _clickToBack.call()
    }

    fun setGalleryImage(imagePath: String) {
        _thumbNail.value = imagePath
        _date.value = Date()
    }

    fun deleteImage() {
        _thumbNail.value = null
        _date.value = null
    }

    fun upload() {
        isAllowUpload(_thumbNail.value, _growType.value) { allow, message ->
            if (allow) {
                uploadSubject.onNext(uploadRequest())
            } else {
                _showToast.value = message
            }
        }
    }

    fun checkedType(type: String) {
        _growType.value = type
    }

    private fun isAllowUpload(
        thumbnail: String?,
        growType: String?,
        action: (Boolean, String) -> Unit
    ) {
        when {
            thumbnail.isNullOrEmpty() -> action(false, "사진을 등록해주세요.")
            growType.isNullOrEmpty() -> action(false, "상태를 체크해주세요.")
            else -> action(true, "")
        }
    }

    private fun uploadRequest() = PlantImageRequest(
        _date.value ?: Date(),
        _thumbNail.value ?: "",
        plantId,
        _growType.value ?: ""
    )
}