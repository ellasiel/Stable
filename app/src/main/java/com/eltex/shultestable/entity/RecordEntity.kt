package com.eltex.shultestable.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eltex.shultestable.model.Record

@Entity(tableName = "Records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,
    @ColumnInfo(name = "numberTime")
    var numberTime: String = "",
    @ColumnInfo(name = "mode")
    val mode: String = "",
    @ColumnInfo(name = "level")
    val level: String = "",
    @ColumnInfo(name = "time")
    val time: String = "",
    @ColumnInfo(name = "mistakes")
    val mistakes: String = "",
) {
    companion object {
        fun fromRecord(record: Record): RecordEntity = with(record) {
            RecordEntity(
                id = id,
                numberTime = numberTime,
                mode = mode,
                level = level,
                time = time,
                mistakes = mistakes
            )
        }
    }

    fun toRecord(): Record =
        Record(
            id = id,
            numberTime = numberTime,
            mode = mode,
            level = level,
            time = time,
            mistakes = mistakes
        )
}