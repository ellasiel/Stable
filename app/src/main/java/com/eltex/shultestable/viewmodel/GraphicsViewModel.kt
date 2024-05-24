package com.eltex.shultestable.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.shultestable.model.GameRecord
import com.eltex.shultestable.repository.RecordRepository
import kotlinx.coroutines.flow.Flow

class GraphicsViewModel(private val recordRepository: RecordRepository) : ViewModel() {
        fun getAllRecordsByModeAndLevel(mode: String, level: String): Flow<List<GameRecord>> {
        return recordRepository.getAllRecordsByModeAndLevel(mode, level)
    }
}