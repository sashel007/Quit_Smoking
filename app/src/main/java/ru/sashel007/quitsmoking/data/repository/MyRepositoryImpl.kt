package ru.sashel007.quitsmoking.data.repository

import android.util.Log
import ru.sashel007.quitsmoking.data.db.dao.AchievementDao
import ru.sashel007.quitsmoking.data.db.dao.UserDao
import ru.sashel007.quitsmoking.data.db.entity.AchievementData
import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.data.repository.dto.UserDto
import ru.sashel007.quitsmoking.data.repository.dto.mappers.AchievementMapper.toDto
import ru.sashel007.quitsmoking.data.repository.dto.mappers.UserMapper.toDto
import ru.sashel007.quitsmoking.data.repository.dto.mappers.UserMapper.toEntity
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val achievementDao: AchievementDao
) {

    suspend fun insertUser(userDto: UserDto) {
        val userData = userDto.toEntity()
        userDao.insert(userData)
    }

    suspend fun deleteUser(userDto: UserDto) {
        val userData = userDto.toEntity()
        userDao.delete(userData)
    }

    suspend fun getUserData(id: Int): UserDto {
        return try {
            val userDao = userDao.getUserById(1)
            Log.d("FirstMonthWithoutSmokingPage", "repository: ${userDao.toDto()}")
            userDao.toDto()
        } catch (e: Exception) {
            Log.e("FirstMonthWithoutSmokingPage", "кэтч в getUserData", e)
            e.printStackTrace()
            UserDto(0, 0, 0, 0)
        }
    }

    suspend fun insertAchievements(achievements: List<AchievementData>) {
        achievementDao.insertAll(achievements)
    }

    suspend fun updateAchievement(achievement: AchievementData) {
        achievementDao.updateAchievement(achievement)
    }

    suspend fun getAchievement(id: Int): AchievementDto {
        return try {
            val achievementDaoEntity = achievementDao.getAchievement(id)
            achievementDaoEntity.toDto()
        } catch (e: Exception) {
            AchievementDto(0, "_", "_", "_", false, 0, 0)
        }
    }

    suspend fun getAllAchievements(): List<AchievementDto> {
        var achievementsDtoList: List<AchievementDto> = mutableListOf()

        try {
            val achievementsEntityLists = achievementDao.getAllAchievements()
            achievementsDtoList = achievementsEntityLists.toDto()
        } catch (e: Exception) {
            Logger.getLogger("My_Logger").log(Level.INFO, "Список достижений не получен")
        }
        return achievementsDtoList
    }

    suspend fun updateQuitTimeInMillisec(id: Int, quitTimeInMillisec: Long) {
        val currentUserData = userDao.getUserById(id)
        Log.d("TEST_2_currentUserData", "$currentUserData")
        if (currentUserData != null) {
            val updatedUserData = currentUserData.copy(quitTimeInMillisec = quitTimeInMillisec)
            userDao.update(updatedUserData)
        } else {
            val newUserData = UserData(id = id, quitTimeInMillisec = quitTimeInMillisec, 0, 0, 0)
            userDao.insert(newUserData)
        }
    }

    suspend fun updateCigarettesPerDay(id: Int, cigarettesPerDay: Int) {
        try {
            val currentUserData = userDao.getUserById(id) ?: return
            val updatedUserData = currentUserData.copy(cigarettesPerDay = cigarettesPerDay)
            userDao.update(updatedUserData)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error in updateCigarettesPerDay", e)
        }
    }

    suspend fun updateCigarettesInPack(id: Int, cigarettesInPack: Int) {
        val currentUserData = userDao.getUserById(id) ?: return
        val updatedUserData = currentUserData.copy(cigarettesInPack = cigarettesInPack)
        userDao.update(updatedUserData)
    }

    suspend fun updatePackCost(id: Int, packCost: Int) {
        val currentUserData = userDao.getUserById(id) ?: return
        val updatedUserData = currentUserData.copy(packCost = packCost)
        userDao.update(updatedUserData)
    }

}
