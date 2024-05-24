package com.eltex.shultestable.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eltex.shultestable.dao.LevelDao
import com.eltex.shultestable.dao.ModeDao
import com.eltex.shultestable.dao.RecordDao
import com.eltex.shultestable.entity.LevelEntity
import com.eltex.shultestable.entity.ModeEntity
import com.eltex.shultestable.entity.RecordEntity

@Database(
    entities = [RecordEntity::class, ModeEntity::class, LevelEntity::class],
    version = 2,
)
abstract class AppDb : RoomDatabase() {
    abstract val recordDao: RecordDao
    abstract fun modeDao(): ModeDao
    abstract fun levelDao(): LevelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb {
            INSTANCE?.let { return it }

            synchronized(this) {
                INSTANCE?.let { return it }

                val appDb = Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "app_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                //context.deleteDatabase("app_db")
                INSTANCE = appDb
                //populateInitialData(appDb)
                return appDb
            }
        }

        private fun populateInitialData(database: AppDb) {
            val modeDao = database.modeDao()
            val levelDao = database.levelDao()
            val modes = listOf(
                ModeEntity(name = "speed"),
                ModeEntity(name = "memory")
            )
            val levels = listOf(
                LevelEntity(name = "easy"),
                LevelEntity(name = "normal"),
                LevelEntity(name = "hard")
            )
            modeDao.insertAll(modes)
            levelDao.insertAll(levels)
        }
    }
}