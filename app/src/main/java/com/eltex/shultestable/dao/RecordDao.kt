package com.eltex.shultestable.dao
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eltex.shultestable.entity.RecordEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface RecordDao {
    @Query("SELECT * FROM Records ORDER BY id DESC")
    fun getAll(): Flow<List<RecordEntity>>
    @Upsert
    fun save(event: RecordEntity): Long
    @Query("DELETE FROM Records WHERE id = :id ")
    fun deleteById(id: Long)
}