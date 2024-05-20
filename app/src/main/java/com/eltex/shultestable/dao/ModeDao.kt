package com.eltex.shultestable.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.eltex.shultestable.entity.ModeEntity

@Dao
interface ModeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(modes: List<ModeEntity>)
}