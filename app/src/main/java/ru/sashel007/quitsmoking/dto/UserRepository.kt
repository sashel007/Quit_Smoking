package ru.sashel007.quitsmoking.dto

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val userData: LiveData<UserData> = userDao.getUserData(1) // Example ID

    suspend fun insert(userData: UserData) {
        userDao.insert(userData)
    }
    // ... other functions for update, delete, etc.
}
