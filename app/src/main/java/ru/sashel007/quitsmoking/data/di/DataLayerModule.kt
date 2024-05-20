package ru.sashel007.quitsmoking.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.db.AppDatabase
import ru.sashel007.quitsmoking.data.db.AppDatabase.Companion.getDatabase
import ru.sashel007.quitsmoking.data.db.dao.AchievementDao
import ru.sashel007.quitsmoking.data.db.dao.UserDao
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.util.AchievementSerialization
import ru.sashel007.quitsmoking.util.MySharedPref
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataLayerModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        Log.d("HiltModule", "Providing database instance")
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "app_database")
            .addCallback(databaseCallback(appContext))
            .fallbackToDestructiveMigration()
            .build().also {
                Log.d("HiltModule", "Database built")
            }
    }

    @Provides
    fun databaseCallback(@ApplicationContext context: Context): RoomDatabase.Callback {
        Log.d("HiltModule", "Creating database callback")
        return object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("HiltModule", "onCreate callback triggered")
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val database = getDatabase(context)
                        val achievementDao = database.achievementDao()
                        val jsonUtils = AchievementSerialization(context)
                        Log.d("HiltModule", "Calling loadAchievementsFromJson")
                        val achievements = jsonUtils.loadAchievementsFromJson()
                        if (achievements.isNotEmpty()) {
                            achievementDao.insertAll(achievements)
                            Log.d("HiltModule", "Achievements inserted successfully")
                        } else {
                            Log.d("HiltModule", "No achievements to insert")
                        }
                    } catch (e: Exception) {
                        Log.e("HiltModule", "Error in onCreate callback", e)
                    }
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideAchievementSerialization(@ApplicationContext context: Context): AchievementSerialization {
        return AchievementSerialization(context)
    }

    @Provides
    @Singleton
    fun provideAchievementDao(appDatabase: AppDatabase): AchievementDao {
        return appDatabase.achievementDao()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideMyRepositoryImpl(userDao: UserDao, achievementDao: AchievementDao): MyRepositoryImpl {
        return MyRepositoryImpl(userDao, achievementDao)
    }

    @Provides
    @Singleton
    fun provideMySharedPref(@ApplicationContext context: Context): MySharedPref {
        return MySharedPref(context)
    }
}