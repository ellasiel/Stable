package com.eltex.shultestable.repository

import com.eltex.shultestable.dao.RecordDao
import com.eltex.shultestable.entity.RecordEntity
import com.eltex.shultestable.model.Record
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class SQLiteRecordRepository(private val dao: RecordDao) : RecordRepository {
    override fun getRecords(): Flow<List<Record>> = dao.getAll()
        .map {
            it.map(RecordEntity::toRecord)
        }

    override fun insert(record: Record) {
        val recordEntity = RecordEntity.fromRecord(record)
        dao.insert(recordEntity)
    }
    override fun getLastRecord(): Record? {
        return dao.getLastRecord()?.toRecord()
    }
    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}