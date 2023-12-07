package ru.sashel007.quitsmoking.dto

import android.util.Log
import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val userData: LiveData<UserData> = userDao.getUserData(1) // Example ID
    init {
        Log.d("UserData", "userData: ${userData.value}")
    }
    suspend fun insert(userData: UserData) {
        userDao.insert(userData)
    }

    suspend fun delete(userData: UserData) {
        userDao.delete(userData)
    }

//    suspend fun update(userData: UserData) {
//        try {
//            userDao.update(userData)
//            Log.d("UserRepository", "User updated successfully")
//        } catch (e: Exception) {
//            Log.e("UserRepository", "Error updating user", e)
//        }
//    }

    suspend fun getUserData(id: Int) = userDao.getUserByIdAsync(id)

    suspend fun updateQuitDate(id: Int, quitDate: Long) {
        val currentUserData = userDao.getUserData(id).value ?: return
        val updatedUserData = currentUserData.copy(quitDate = quitDate)
        userDao.update(updatedUserData)
    }

    suspend fun updateQuitTime(id: Int, quitTime: Int) {
        val currentUserData = userDao.getUserByIdAsync(id)
        val updatedUserData = currentUserData?.copy(quitTime = quitTime)
        if (updatedUserData != null) {
            userDao.update(updatedUserData)
        }
    }
    suspend fun updateCigarettesPerDay(id: Int, cigarettesPerDay: Int) {
        Log.d("UserRepository", "Request to update cigarettesPerDay to $cigarettesPerDay")
        try {
            // Получаем данные пользователя асинхронно
            val currentUserData = userDao.getUserByIdAsync(id)
            val updatedUserData = currentUserData?.copy(cigarettesPerDay = cigarettesPerDay)
            if (updatedUserData != null) {
                Log.d("UserRepository", "Updating user: $updatedUserData")
                userDao.update(updatedUserData)
            } else {
                throw Exception("User data not found")
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error in updateCigarettesPerDay", e)
        }
    }

    suspend fun updateCigarettesInPack(id: Int, cigarettesInPack: Int) {
        val currentUserData = userDao.getUserByIdAsync(id)
        val updatedUserData = currentUserData?.copy(cigarettesInPack = cigarettesInPack)
        if (updatedUserData != null) {
            userDao.update(updatedUserData)
        }
    }

    suspend fun updatePackCost(id: Int, packCost: Int) {
        val currentUserData = userDao.getUserByIdAsync(id)
        val updatedUserData = currentUserData?.copy(packCost = packCost)
        if (updatedUserData != null) {
            Log.d("UserRepository", "Updating user: $updatedUserData")
            userDao.update(updatedUserData)
        }
    }

    fun getAllUserData(): LiveData<List<UserData>> {
        return userDao.getAllUserData()
    }
}
