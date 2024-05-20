package com.eltex.shultestable.db

object RecordTable {
    const val TABLE_NAME = "Records"
    const val ID = "id"
    const val NUMBERTIME = "numberTime"
    const val MODE_ID = "mode_id"
    const val LEVEL_ID = "level_id"
    const val TIME = "time"
    const val MISTAKES = "mistakes"
    val allColumns =
        arrayOf(ID, LEVEL_ID, MODE_ID, TIME, NUMBERTIME, MISTAKES)
}