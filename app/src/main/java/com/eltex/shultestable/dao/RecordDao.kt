package com.eltex.shultestable.dao
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eltex.shultestable.entity.RecordEntity
import com.eltex.shultestable.entity.RecordWithModeAndLevel
import kotlinx.coroutines.flow.Flow
@Dao
interface RecordDao {
    @Query("SELECT * FROM Records ORDER BY id DESC")
    fun getAll(): Flow<List<RecordWithModeAndLevel>>

    @Upsert
    fun insert(event: RecordEntity): Long

    @Query("SELECT * FROM Records ORDER BY id DESC LIMIT 1")
    fun getLastRecord(): RecordWithModeAndLevel?

    @Query("DELETE FROM Records WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT id FROM Mode WHERE name = :name")
    fun getModeIdByName(name: String): Long?

    @Query("SELECT id FROM Level WHERE name = :name")
    fun getLevelIdByName(name: String): Long?
}