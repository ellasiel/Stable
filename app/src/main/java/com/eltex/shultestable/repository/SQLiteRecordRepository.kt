package com.eltex.shultestable.repository

import com.eltex.shultestable.dao.RecordDao
import com.eltex.shultestable.entity.RecordEntity
import com.eltex.shultestable.model.TrainRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SQLiteRecordRepository(private val dao: RecordDao) : RecordRepository {

    override fun getRecords(): Flow<List<TrainRecord>> = dao.getAll()
        .map { recordWithModeAndLevelList ->
            recordWithModeAndLevelList.map { recordWithModeAndLevel ->
                recordWithModeAndLevel.toGameRecord()
            }
        }

    override fun insert(record: TrainRecord) {
        val modeId = dao.getModeIdByName(record.mode)
            ?: throw IllegalArgumentException("Invalid mode: ${record.mode}")
        val levelId = dao.getLevelIdByName(record.level)
            ?: throw IllegalArgumentException("Invalid level: ${record.level}")
        val recordEntity = RecordEntity.fromGameRecord(record, modeId, levelId)
        dao.insert(recordEntity)
    }

    override fun getLastRecord(): TrainRecord? {
        return dao.getLastRecord()?.toGameRecord()
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun getAllRecordsByModeAndLevel(mode: String, level: String): Flow<List<TrainRecord>> {
        return dao.getAllRecordsByModeAndLevel(mode, level)
    }
}