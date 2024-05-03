package com.eltex.shultestable.repository

import com.eltex.shultestable.model.Record
import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    fun getRecords(): Flow<List<Record>>
    fun deleteById(id: Long)
}