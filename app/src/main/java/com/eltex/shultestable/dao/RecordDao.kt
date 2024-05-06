package com.eltex.shultestable.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.eltex.shultestable.entity.RecordEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface RecordDao {
    @Query("SELECT * FROM Records ORDER BY id DESC")
    fun getAll(): Flow<List<RecordEntity>>
    @Upsert
    fun insert(event: RecordEntity): Long

}