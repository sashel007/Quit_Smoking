package ru.sashel007.quitsmoking.data.repository

import androidx.lifecycle.LiveData
import ru.sashel007.quitsmoking.data.db.dao.AchievementDao
import ru.sashel007.quitsmoking.data.db.entity.AchievementData

class AchievementRepository(private val dao: AchievementDao) {
//    val allAchievements: List<AchievementData> = dao.getAllAchievements()

    suspend fun insertAchievements(achievements: List<AchievementData>) {
        dao.insertAll(achievements)
    }

    suspend fun updateAchievement(achievement: AchievementData) {
        dao.updateAchievement(achievement)
    }
}