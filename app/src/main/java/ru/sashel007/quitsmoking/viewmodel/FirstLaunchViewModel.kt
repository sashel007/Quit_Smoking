package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.util.AppState
import ru.sashel007.quitsmoking.util.MySharedPref

class FirstLaunchViewModel(
    private val sharedPref: MySharedPref
) : ViewModel() {

    private val _launchState = MutableStateFlow<AppState>(AppState.FIRST_LAUNCH)
    val launchState: StateFlow<AppState> = _launchState

    init {
        checkFirstLaunch()
    }

    private fun checkFirstLaunch() {
        viewModelScope.launch {
            val isFirstLaunchedValue = sharedPref.checkIsFirstLaunch()

            if (isFirstLaunchedValue) {
                _launchState.value = AppState.FIRST_LAUNCH
                sharedPref.setFirstLaunch(false)
            } else {
                _launchState.value = AppState.SUBSEQUENT_LAUNCH
            }
        }
    }

    class FirstLaunchViewModelFactory(private val sharedPref: MySharedPref) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FirstLaunchViewModel(sharedPref) as T
        }
    }
}