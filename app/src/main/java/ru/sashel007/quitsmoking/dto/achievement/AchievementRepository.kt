package ru.sashel007.quitsmoking.dto.achievement

import androidx.lifecycle.LiveData

class AchievementRepository(private val dao: AchievementDao) {
    val allAchievements: LiveData<List<AchievementData>> = dao.getAllAchievements()

    suspend fun insertAchievements(achievements: List<AchievementData>) {
        dao.insertAll(achievements)
    }

    suspend fun updateAchievement(achievement: AchievementData) {
        dao.updateAchievement(achievement)
    }
}