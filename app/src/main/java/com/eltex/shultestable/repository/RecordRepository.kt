package com.eltex.shultestable.repository

import com.eltex.shultestable.model.GameRecord
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getRecords(): Flow<List<GameRecord>>
    fun insert(record: GameRecord)
    fun getLastRecord(): GameRecord?
    fun deleteById(id: Long)
}