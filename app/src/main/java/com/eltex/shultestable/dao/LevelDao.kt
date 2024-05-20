package com.eltex.shultestable.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.eltex.shultestable.entity.LevelEntity

@Dao
interface LevelDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(levels: List<LevelEntity>)
}