package ru.sashel007.quitsmoking.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.db.dao.AchievementDao
import ru.sashel007.quitsmoking.data.db.dao.UserDao
import ru.sashel007.quitsmoking.data.db.entity.AchievementData
import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.util.AchievementSerialization

@Database(entities = [UserData::class, AchievementData::class], version = 12, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Log.d("JSON Loading","Получение экземпляра AppDatabase")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
//                    .addMigrations(object : Migration(4, 5) {
//                        override fun migrate(db: SupportSQLiteDatabase) {
//                            db.execSQL(
//                                """
//                        CREATE TABLE IF NOT EXISTS `achievements` (
//                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                        `name` TEXT NOT NULL,
//                        `description` TEXT NOT NULL,
//                        `imageUri` TEXT NOT NULL,
//                        `isUnlocked` INTEGER NOT NULL,
//                        `progressLine` INTEGER NOT NULL)
//                    """
//                            )
//                        }
//                    })
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.let { database ->
                                    val userDao = database.userDao()
                                    userDao.insert(
                                        UserData(
                                            id = 1,
                                            quitTimeInMillisec = 0L,
                                            cigarettesPerDay = 0,
                                            cigarettesInPack = 0,
                                            packCost = 0
                                        )
                                    )
                                    val achievementDao = database.achievementDao()
                                    val jsonUtils = AchievementSerialization(context)
                                    Log.d("JSON Loading","Дощёл ли сюда код_1")
                                    val achievements = jsonUtils.loadAchievementsFromJson()
                                    Log.d("JSON Loading", "Loaded: ${achievements.size} achievements")
                                    achievements.forEach { Log.d("JSON Loading", "Name: ${it.name}, Unlocked: ${it.isUnlocked}") }
                                    Log.d("JSON Loading","Дощёл ли сюда код_2")
                                    achievementDao.insertAll(achievements)
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                Log.d("JSON Loading", "Database instance created or retrieved")
                instance
            }
        }
    }
}

