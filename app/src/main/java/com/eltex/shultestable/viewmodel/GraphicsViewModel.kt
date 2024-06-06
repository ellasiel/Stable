package com.eltex.shultestable.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.shultestable.model.TrainRecord
import com.eltex.shultestable.repository.RecordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GraphicsViewModel(private val recordRepository: RecordRepository) : ViewModel() {

    fun getAllRecordsByModeAndLevel(mode: String, level: String): Flow<List<TrainRecord>> {
        return recordRepository.getAllRecordsByModeAndLevel(mode, level)
    }

    fun getBestResultByModeAndLevel(mode: String, level: String): Flow<TrainRecord?> {
        return recordRepository.getAllRecordsByModeAndLevel(mode, level)
            .map { records -> records.minByOrNull { it.time } }
    }
}