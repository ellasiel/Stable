package com.eltex.shultestable.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpeedGameViewModel(private val recordRepository: RecordRepository) : ViewModel() {
    val currentNumber = MutableLiveData<Int>()

    fun checkNumber(number: Int, maxNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentNumber.value?.let { currentValue ->
                if (number == currentValue && (number + 1) <= maxNumber) {
                    currentNumber.postValue(number + 1)
                }
            }
        }
    }

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            currentNumber.postValue(1)
        }
    }

    fun saveResultTime(model: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.insert(model)
        }
    }
}