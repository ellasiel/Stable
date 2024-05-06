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
    @ColumnInfo(name = "level")
    val level: String = "",
    @ColumnInfo(name = "time")
    val time: String = "",
) {
    companion object {
        fun fromRecord(record: Record): RecordEntity = with(record) {
            RecordEntity(
                level = level,
                time = time,
            )
        }
    }

    fun toRecord(): Record =
        Record(
            level = level,
            time = time
        )
}