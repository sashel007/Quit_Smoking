package ru.sashel007.quitsmoking.data.repository

import android.util.Log
import ru.sashel007.quitsmoking.data.db.dao.UserDao
import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.data.repository.dto.UserDto
import ru.sashel007.quitsmoking.data.repository.dto.UserMapper.toDto
import ru.sashel007.quitsmoking.data.repository.dto.UserMapper.toEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(userDto: UserDto) {
        val userData = userDto.toEntity()
        userDao.insert(userData)
    }

    suspend fun delete(userDto: UserDto) {
        val userData = userDto.toEntity()
        userDao.delete(userData)
    }

    suspend fun getUserData(id: Int): UserDto {
        return try {
            val userDao = userDao.getUserById(1)
            userDao.toDto()
        } catch (e: Exception) {
            UserDto(0, 0, 0, 0, 0)
        }
    }

    suspend fun getAllUserData(): List<UserDto> {
        val userDaoList = userDao.getAllUserData()
        return userDaoList.toDto()
    }

    suspend fun updateQuitDate(id: Int, quitDate: Long) {
        val currentUserData = userDao.getUserById(id) ?: return
        val updatedUserData = currentUserData.copy(quitDate = quitDate)
        userDao.update(updatedUserData)
    }

    suspend fun updateQuitTime(id: Int, quitTime: Int) {
        val currentUserData = userDao.getUserById(id)
        val updatedUserData = currentUserData.copy(quitTime = quitTime)
        userDao.update(updatedUserData)
    }

    suspend fun updateCigarettesPerDay(id: Int, cigarettesPerDay: Int) {
        Log.d("UserRepository", "Request to update cigarettesPerDay to $cigarettesPerDay")
        try {
            val currentUserData = userDao.getUserById(id)
            val updatedUserData = currentUserData.copy(cigarettesPerDay = cigarettesPerDay)
            Log.d("UserRepository", "Updating user: $updatedUserData")
            userDao.update(updatedUserData)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error in updateCigarettesPerDay", e)
        }
    }

    suspend fun updateCigarettesInPack(id: Int, cigarettesInPack: Int) {
        val currentUserData = userDao.getUserById(id)
        val updatedUserData = currentUserData.copy(cigarettesInPack = cigarettesInPack)
        userDao.update(updatedUserData)
    }

    suspend fun updatePackCost(id: Int, packCost: Int) {
        val currentUserData = userDao.getUserById(id)
        val updatedUserData = currentUserData.copy(packCost = packCost)
        userDao.update(updatedUserData)
    }

}
