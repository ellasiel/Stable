package com.eltex.shultestable.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eltex.shultestable.dao.RecordDao
import com.eltex.shultestable.entity.RecordEntity
@Database(
    entities = [RecordEntity::class],
    version = 1,
)
abstract class AppDb : RoomDatabase() {
    abstract val recordDao: RecordDao
    // companion object всегда 1 в приложении
    companion object {
        // Глобальная переменная в 1 экземпляре
        @Volatile
        private var INSTANCE: AppDb? = null
        fun getInstance(context: Context): AppDb {
            // Если до этого создавали INSTANCE, то возвращаем
            INSTANCE?.let { return it }
            // На всякий случай возьмём applicationContext, если случайно передали Activity или Service
            val application = context.applicationContext
            synchronized(this) {
                INSTANCE?.let { return it }
                val appDb = Room.databaseBuilder(application, AppDb::class.java, "app_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = appDb
                return appDb
            }
        }
    }
}