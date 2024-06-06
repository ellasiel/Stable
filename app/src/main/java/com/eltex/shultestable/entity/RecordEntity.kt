package com.eltex.shultestable.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.eltex.shultestable.model.TrainRecord

@Entity(
    tableName = "Records",
    foreignKeys = [
        ForeignKey(
            entity = ModeEntity::class,
            parentColumns = ["id"],
            childColumns = ["mode_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LevelEntity::class,
            parentColumns = ["id"],
            childColumns = ["level_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L,

    @ColumnInfo(name = "numberTime")
    val numberTime: String = "",

    @ColumnInfo(name = "mode_id")
    val modeId: Long = 0L,

    @ColumnInfo(name = "level_id")
    val levelId: Long = 0L,

    @ColumnInfo(name = "time")
    val time: String = "",

    @ColumnInfo(name = "mistakes")
    val mistakes: String = "",
) {
    companion object {
        fun fromGameRecord(trainRecord: TrainRecord, modeId: Long, levelId: Long): RecordEntity =
            with(trainRecord) {
                RecordEntity(
                    id = id,
                    numberTime = numberTime,
                    modeId = modeId,
                    levelId = levelId,
                    time = time,
                    mistakes = mistakes
                )
            }
    }

    fun toGameRecord(mode: String, level: String): TrainRecord =
        TrainRecord(
            id = id,
            numberTime = numberTime,
            mode = mode,
            level = level,
            time = time,
            mistakes = mistakes
        )
}