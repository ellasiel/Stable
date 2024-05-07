package com.eltex.shultestable.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpeedGameViewModel(private val recordRepository: RecordRepository) : ViewModel() {
    val actualNumber = MutableLiveData<Int>()
    val mistakesCount = MutableLiveData<Int>()

    init {
        mistakesCount.value = 0
    }

    fun checkNumber(number: Int, maxNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            actualNumber.value?.let { currentValue ->
                if (number == currentValue && (number + 1) <= maxNumber) {
                    actualNumber.postValue(number + 1)
                } else {
                    // Увеличиваем счетчик ошибок при неправильном нажатии
                    mistakesCount.postValue(mistakesCount.value?.plus(1))
                }
            }
        }
    }

    fun startGame() {
        viewModelScope.launch(Dispatchers.IO) {
            actualNumber.postValue(1)
        }
    }

    fun saveResultTime(model: Record) {
        viewModelScope.launch(Dispatchers.IO) {
            recordRepository.insert(model)
        }
    }

    fun getLastRecordId(): Long {
        // Выполните запрос к базе данных, чтобы получить последнюю запись
        val lastRecord = recordRepository.getLastRecord()

        // Верните ID последней записи
        return lastRecord?.id ?: 0L
    }
}