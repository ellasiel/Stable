package com.eltex.shultestable.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DbHelper(context: Context) : SQLiteOpenHelper(context, "app_db", null, 2) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE ${RecordTable.TABLE_NAME} (
                ${RecordTable.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${RecordTable.LEVEL} TEXT NOT NULL,
                ${RecordTable.TIME} TEXT NOT NULL
            );
        """.trimIndent()
        )
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}