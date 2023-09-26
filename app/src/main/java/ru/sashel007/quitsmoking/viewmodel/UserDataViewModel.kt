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

    // ... other functions to handle updates, deletes, etc.
}

