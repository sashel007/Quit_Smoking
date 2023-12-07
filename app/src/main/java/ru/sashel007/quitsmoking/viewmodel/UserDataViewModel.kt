package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.dto.AppDatabase
import ru.sashel007.quitsmoking.dto.UserDao
import ru.sashel007.quitsmoking.dto.UserData
import ru.sashel007.quitsmoking.dto.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    val userData: LiveData<UserData> = repository.userData
    val userId = 1
    val allUserData: LiveData<List<UserData>> = repository.getAllUserData()

    fun insert(userData: UserData) = viewModelScope.launch {
        repository.insert(userData)
    }

    fun delete(userData: UserData) = viewModelScope.launch {
        repository.delete(userData)
    }

    fun getUserData(id: Int) = viewModelScope.launch {
        repository.getUserData(id)
    }

    fun updateQuitDate(quitDate: Long) {
        viewModelScope.launch {
            repository.updateQuitDate(userId, quitDate)
        }
    }

    fun updateQuitTime(quitTime: Int) {
        viewModelScope.launch {
            repository.updateQuitTime(userId, quitTime)
        }
    }

    fun updateCigarettesPerDay(cigarettesPerDay: Int) {
        Log.d("UserViewModel", "Updating cigarettesPerDay to $cigarettesPerDay")
        viewModelScope.launch {
            repository.updateCigarettesPerDay(userId, cigarettesPerDay)
        }
    }

    fun updateCigarettesInPack(cigarettesInPack: Int) {
        viewModelScope.launch {
            repository.updateCigarettesInPack(userId, cigarettesInPack)
        }
    }

    fun updatePackCost(packCost: Int) {
        viewModelScope.launch {
            repository.updatePackCost(userId, packCost)
        }
    }

    fun fetchAllUserData(): LiveData<List<UserData>> {
        return repository.getAllUserData()
    }

//    fun refreshUserData() = viewModelScope.launch {
//        val updatedUserData = repository.getUserByIdAsync(userId)
//        // Теперь обновляем LiveData
//        if (updatedUserData != null) {
//            _userData.value = updatedUserData
//        }
//    }
}