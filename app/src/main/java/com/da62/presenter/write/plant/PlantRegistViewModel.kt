package com.da62.presenter.write.plant

import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.da62.datasource.local.PreferenceStorage
import com.da62.model.SavePlant
import com.da62.presenter.base.BaseViewModel
import com.da62.presenter.write.plant.fragment.Card
import com.da62.usecase.PlantRegistUseCase
import com.da62.util.SingleLiveEvent
import com.da62.util.add

class PlantRegistViewModel(
    private val useCase: PlantRegistUseCase,
    private val preferenceStorage: PreferenceStorage
) : BaseViewModel() {

    private val _error = SingleLiveEvent<String>()

    val error: LiveData<String> = _error

    private val _next = SingleLiveEvent<Any>()

    val next: LiveData<Any> = _next

    private val _speciesImage = MutableLiveData<Card>()

    val speciesImage: LiveData<Card> = _speciesImage

    val species = SingleLiveEvent<String>()

    private val _searchResult = SingleLiveEvent<List<String>>()

    val searchResult: LiveData<List<String>> = _searchResult

    private val _isSpeciesSearching = SingleLiveEvent<Boolean>()

    val isSpeciesSearching: LiveData<Boolean> = _isSpeciesSearching

    val nickName = SingleLiveEvent<String>()

    private val _plantLevel = SingleLiveEvent<String>()

    private val plantLevel: LiveData<String> = _plantLevel

    val waterDate = SingleLiveEvent<String>()

    val waterOften = SingleLiveEvent<String>().apply {
        value = "0"
    }

    val waterTime = SingleLiveEvent<String>()

    val isWaterAlarmChecked = SingleLiveEvent<Boolean>().apply {
        value = false
    }

    fun inputSpeciesText(input: String) {
        getToken()?.let { token ->
            _isSpeciesSearching.postValue(true)
            compositeDisposable add useCase.getPlantNames(token, input)
                .subscribe({
                    _isSpeciesSearching.postValue(false)
                    _searchResult.value = it
                }, {
                    _isSpeciesSearching.postValue(false)
                    it.printStackTrace()
                })
        } ?: error("인증실패입니다.")
    }

    fun onSpeciesImageChecked(button: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            _speciesImage.value = button.tag as Card
        } else {
            _speciesImage.value?.let {
                if (it == button.tag) {
                    _speciesImage.value = null
                }
            }
        }
    }

    fun onSpeciesFragmentNextClick(view: View) {
        if (!speciesInputValidate()) {
            _error.value = "빈 칸 없이 입력해주세요."
            return
        }
        if (!speciesImageValidate()) {
            _error.value = "일러스트를 선택해주세요."
            return
        }
        _next.call()
    }

    private fun speciesInputValidate() =
        species.value?.isNotEmpty() ?: false

    private fun speciesImageValidate() =
        speciesImage.value?.let { true } ?: false

    fun onInfoFragmentNextClick(view: View) {
        if (!nickNameInputValidate()) {
            _error.value = "빈 칸 없이 입력해주세요."
            return
        }
        if (!levelValidate()) {
            _error.value = "단계를 선택해주세요."
            return
        }
        _next.call()
    }

    private fun nickNameInputValidate() =
        nickName.value?.isNotEmpty() ?: false

    private fun levelValidate() =
        _plantLevel.value?.let { true } ?: false

    fun setPlantLevel(level: String) {
        _plantLevel.value = level
    }

    fun onWaterFragmentCompleteClick(view: View) {
        if (!waterDateValidate()) {
            _error.value = "날짜를 입력해주세요."
            return
        }
        if (!waterOftenValidate()) {
            _error.value = "주기를 입력해주세요."
            return
        }
        if (!waterTimeValidate()) {
            _error.value = "시간을 입력해주세요."
            return
        }
        save()
    }

    private fun waterDateValidate() =
        waterDate.value?.let { true } ?: false

    private fun waterOftenValidate() =
        waterOften.value?.let { it.toInt() > 0 } ?: throw IllegalStateException()

    private fun waterTimeValidate() =
        waterTime.value?.let { true } ?: false

    private fun save() {
        getToken()?.let { token ->
            val id = getUserId()
            if (id != 0) {
                compositeDisposable add useCase.savePlant(
                    token,
                    SavePlant(
                        alarm = false,
                        card = speciesImage.value!!.name,
                        grow = plantLevel.value!!,
                        kind = species.value!!,
                        name = nickName.value!!,
                        raiseDate = toLocalDateTimeFormat(),
                        waterDate = waterOften.value!!.toInt(),
                        waterTime = toLocalDateTimeFormat()
                    ),
                    getUserId()
                ).subscribe({
                    _next.call()
                }, {
                    _error.value = "서버오류입니다. 다시 시도해주세요."
                })
            } else {
                error("로그인 오류입니다.")
            }
        } ?: error("로그인 오류입니다.")
    }

    private fun toLocalDateTimeFormat(): String {
        val yearMonthDay = waterDate.value!!.split(".")
        val hourMin = waterTime.value!!.split(":")
        val year = yearMonthDay[0]
        val month = yearMonthDay[1]
        val day = yearMonthDay[2]
        val hour = hourMin[0]
        val min = hourMin[1]
        return "$year-${addZero(month)}-${addZero(day)}T${addZero(hour)}:${addZero(min)}:00Z"
    }

    private fun addZero(value: String) = if (value.length == 1) {
        "0$value"
    } else {
        value
    }

    private fun getToken() = preferenceStorage.accessToken

    private fun getUserId() = preferenceStorage.userId

    private fun error(message: String) {
        _error.postValue(message)
    }
}