package ru.sashel007.quitsmoking.dto

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val userData: LiveData<UserData> = userDao.getUserData(1) // Example ID

    suspend fun insert(userData: UserData) {
        userDao.insert(userData)
    }

    suspend fun update(userData: UserData) {
        userDao.update(userData)
    }

    suspend fun delete(userData: UserData) {
        userDao.delete(userData)
    }

    suspend fun getUserData(id: Int) = userDao.getUserData(id)

}
