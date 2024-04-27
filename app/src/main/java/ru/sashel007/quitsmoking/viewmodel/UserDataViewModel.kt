package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.data.repository.dto.UserDto
import ru.sashel007.quitsmoking.data.repository.dto.mappers.UserMapper.toEntity
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    application: Application,
    private var repository: MyRepositoryImpl
) : AndroidViewModel(application) {

    private val _userData = MutableLiveData<UserDto>()
    val userData: LiveData<UserDto> = _userData

    companion object {
        const val USER_ID = 1
    }

    init {
        loadData()
    }


    private fun loadData() {
        viewModelScope.launch {
            val loadedData = repository.getUserData(USER_ID)
            Log.d("FirstMonthWithoutSmokingPage.kt", "viewmodel: $loadedData")
            _userData.postValue(loadedData)
        }
    }

    fun loadDataForFirstMonthStats() {
        loadData()
    }

    fun insert(userDto: UserDto) = viewModelScope.launch {
        repository.insertUser(userDto)
    }

    fun delete(userDto: UserDto) = viewModelScope.launch {
        repository.deleteUser(userDto)
    }

    fun updateQuitTimeInMillisec(quitTime: Long) {
        viewModelScope.launch {
            repository.updateQuitTimeInMillisec(USER_ID, quitTime)
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
}