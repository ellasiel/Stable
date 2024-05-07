package com.eltex.shultestable.db

object RecordTable {
    const val TABLE_NAME = "Records"
    const val ID = "id"
    const val NUMBERTIME = "numberTime"
    const val MODE = "mode"
    const val LEVEL = "level"
    const val TIME = "time"
    const val MISTAKES = "mistakes"
    val allColumns =
        arrayOf(ID, LEVEL, MODE, TIME, NUMBERTIME, MISTAKES)
}