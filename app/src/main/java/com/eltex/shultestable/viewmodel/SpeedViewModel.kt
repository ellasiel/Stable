package com.eltex.shultestable.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eltex.shultestable.model.Record
import com.eltex.shultestable.repository.RecordRepository

class SpeedViewModel(private val recordRepository: RecordRepository) : ViewModel() {
    val records = MutableLiveData<List<Record>>()

    fun getRecords() {
        TODO("Not yet implemented")
    }
}
