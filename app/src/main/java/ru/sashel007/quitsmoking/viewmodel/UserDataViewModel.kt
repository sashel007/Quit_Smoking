package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.db.AppDatabase
import ru.sashel007.quitsmoking.data.db.dao.UserDao
import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.data.repository.UserRepository
import ru.sashel007.quitsmoking.data.repository.dto.UserDto
import ru.sashel007.quitsmoking.util.MySharedPref

class UserViewModel(
    application: Application,
    private var repository: UserRepository,
    private var userDao: UserDao
) : AndroidViewModel(application) {

    companion object {
        const val USER_ID = 1
    }

    private val _userData = MutableLiveData<UserDto>()
    val userData: LiveData<UserDto> = _userData

    init {
        loadData()
        userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    private fun loadData() {
        viewModelScope.launch {
            val loadedData = repository.getUserData(USER_ID)
            _userData.value = loadedData
        }
    }

    fun insert(userDto: UserDto) = viewModelScope.launch {
        repository.insert(userDto)
    }

    fun delete(userDto: UserDto) = viewModelScope.launch {
        repository.delete(userDto)
    }

    fun getUserData(id: Int) = viewModelScope.launch {
        repository.getUserData(id)
    }

    fun updateQuitDate(quitDate: Long) {
        viewModelScope.launch {
            repository.updateQuitDate(USER_ID, quitDate)
        }
    }

    fun updateQuitTime(quitTime: Int) {
        viewModelScope.launch {
            repository.updateQuitTime(USER_ID, quitTime)
        }
    }

    fun updateCigarettesPerDay(cigarettesPerDay: Int) {
        Log.d("UserViewModel", "Updating cigarettesPerDay to $cigarettesPerDay")
        viewModelScope.launch {
            repository.updateCigarettesPerDay(USER_ID, cigarettesPerDay)
        }
    }

    fun updateCigarettesInPack(cigarettesInPack: Int) {
        viewModelScope.launch {
            repository.updateCigarettesInPack(USER_ID, cigarettesInPack)
        }
    }

    fun updatePackCost(packCost: Int) {
        viewModelScope.launch {
            repository.updatePackCost(USER_ID, packCost)
        }
    }

    class UserViewModelFactory(
        private val application: Application,
        private val repository: UserRepository,
        private val userDao: UserDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(application, repository, userDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}