package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.data.repository.dto.UserDto
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
            val loadedData = repository.getUserData()
            _userData.value = loadedData
            Log.d("Composable_problem", "from loadData(): ${userData.value}")
        }
    }

    fun loadDataForFirstMonthStats() {
        loadData()
    }

    fun updateQuitTimeInMillisec(quitTime: Long) {
        viewModelScope.launch {
            repository.updateQuitTimeInMillisec(USER_ID, quitTime)
            loadData()
        }
    }

    fun updateCigarettesPerDay(cigarettesPerDay: Int) {
        viewModelScope.launch {
            repository.updateCigarettesPerDay(USER_ID, cigarettesPerDay)
            _userData.value?.let {
                _userData.value = it.copy(cigarettesPerDay = cigarettesPerDay)
            }
            Log.d("Composable_problem", "from updateCigarettesPerDay(): ${userData.value}")
        }
    }

    fun updateCigarettesInPack(cigarettesInPack: Int) {
        viewModelScope.launch {
            repository.updateCigarettesInPack(USER_ID, cigarettesInPack)
            _userData.value?.let {
                _userData.value = it.copy(cigarettesInPack = cigarettesInPack)
            }
        }
    }

    fun updatePackCost(packCost: Int) {
        viewModelScope.launch {
            repository.updatePackCost(USER_ID, packCost)
            _userData.value?.let {
                _userData.value = it.copy(packCost = packCost)
            }
        }
    }
}