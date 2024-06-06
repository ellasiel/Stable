package com.eltex.shultestable.repository

import com.eltex.shultestable.model.TrainRecord
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getRecords(): Flow<List<TrainRecord>>
    fun insert(record: TrainRecord)
    fun getLastRecord(): TrainRecord?
    fun deleteById(id: Long)
    fun getAllRecordsByModeAndLevel(mode: String, level: String): Flow<List<TrainRecord>>
}