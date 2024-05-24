package com.eltex.shultestable.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.shultestable.model.GameRecord
import com.eltex.shultestable.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MemoryGameViewModel(private val recordRepository: RecordRepository) : ViewModel() {
    val currentNumber = MutableLiveData<Int>()
    val mistakes2Count = MutableLiveData<Int>()
    val shouldHideNumbers = MutableLiveData<Boolean>()

    init {
        mistakes2Count.value = 0
        shouldHideNumbers.value = false
    }

    fun checkNumber(number: Int, maxNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentNumber.value?.let { currentValue ->
                if (number == currentValue && (number + 1) <= maxNumber) {
                    currentNumber.postValue(number + 1)
                } else {
                    mistakes2Count.postValue(mistakes2Count.value?.plus(1))
                }
            }
        }
    }

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            currentNumber.postValue(1)
            shouldHideNumbers.postValue(false)
            delay(5000)
            shouldHideNumbers.postValue(true)
        }
    }

    fun saveResultTime(model: GameRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.insert(model)
        }
    }

    fun getLastRecordId(): Long {
        val lastRecord = recordRepository.getLastRecord()
        return lastRecord?.id ?: 0L
    }
}