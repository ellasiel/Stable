package com.eltex.shultestable.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.eltex.shultestable.model.GameRecord

data class RecordWithModeAndLevel(
    @Embedded val record: RecordEntity,

    @Relation(
        parentColumn = "mode_id",
        entityColumn = "id"
    )
    val mode: ModeEntity,

    @Relation(
        parentColumn = "level_id",
        entityColumn = "id"
    )
    val level: LevelEntity
) {
    fun toGameRecord(): GameRecord =
        record.toGameRecord(mode.name, level.name)
}