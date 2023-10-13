package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.dto.AppDatabase
import ru.sashel007.quitsmoking.dto.UserData
import ru.sashel007.quitsmoking.dto.UserRepository
import java.util.Date

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    val userData: LiveData<UserData> = repository.userData

    fun insert(userData: UserData) = viewModelScope.launch {
        repository.insert(userData)
    }

    fun update(userData: UserData) = viewModelScope.launch {
        repository.update(userData)
    }

    fun delete(userData: UserData) = viewModelScope.launch {
        repository.delete(userData)
    }

    fun getUserData(id: Int) = viewModelScope.launch {
        repository.getUserData(id)
    }

    fun saveQuitDetails(date: Date, timeInMinutes: Int) = viewModelScope.launch {
        val quitDateInMillis = date.time
        val userData = UserData(
            quitDate = quitDateInMillis,
            quitTime = timeInMinutes,
            cigarettesPerDay = 0,   // По умолчанию 0, пока пользователь не введет значение на следующем экране
            cigarettesInPack = 0,  // Аналогично
            packCost = 0.0         // Аналогично
        )
        repository.insert(userData)
    }

    fun updateCigarettesPerDay(count: Int) = viewModelScope.launch {
        val currentData = userData.value ?: return@launch // Если данных нет, просто вернитесь
        val updatedData = currentData.copy(cigarettesPerDay = count)
        repository.update(updatedData)
    }

    fun updateCigarettesInPack(count: Int) = viewModelScope.launch {
        val currentData = userData.value ?: return@launch
        val updatedData = currentData.copy(cigarettesInPack = count)
        repository.update(updatedData)
    }

    fun updatePackCost(cost: Double) = viewModelScope.launch {
        val currentData = userData.value ?: return@launch
        val updatedData = currentData.copy(packCost = cost)
        repository.update(updatedData)
    }

}

