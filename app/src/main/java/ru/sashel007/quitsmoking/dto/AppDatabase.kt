package ru.sashel007.quitsmoking.dto

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Database(entities = [UserData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Log.d("AppDatabase", "getDatabase called") // Логирование вызова метода getDatabase
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            Log.d("AppDatabase", "onCreate called - initializing database")
                            // Создаем CoroutineScope с IO Dispatcher
                            CoroutineScope(Dispatchers.IO).launch {
                                Log.d("AppDatabase", "Coroutine started for initializing data")
                                // Получаем DAO и выполняем вставку данных
                                val userDao = INSTANCE?.userDao()
                                userDao?.insert(UserData(
                                    // Так как ID автогенерируется, мы не указываем его
                                    quitDate = System.currentTimeMillis(),
                                    quitTime = 0,
                                    cigarettesPerDay = 0,
                                    cigarettesInPack = 0,
                                    packCost = 0
                                ))
                                Log.d("AppDatabase", "Initial data inserted")
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // Логирование создания или получения экземпляра базы данных
                Log.d("AppDatabase", "Database instance created or retrieved")
                instance
            }
        }
    }
}

//put("id", 1)
//put("quitDate", System.currentTimeMillis())
//put("quitTime", 0)
//put("cigarettesPerDay", 0)
//put("cigarettesInPack", 0)
//put("packCost", 0)

