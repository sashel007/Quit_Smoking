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
import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.data.repository.UserRepository
import ru.sashel007.quitsmoking.util.MySharedPref

class UserViewModel(
    application: Application,
    private var repository: UserRepository,
) : AndroidViewModel(application) {

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    val userData: LiveData<UserData> = repository.userData
    private val userId = 1
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

    class UserViewModelFactory(
        private val application: Application,
        private val repository: UserRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserViewModel(application, repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}