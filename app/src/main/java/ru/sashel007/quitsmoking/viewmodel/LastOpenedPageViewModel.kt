package ru.sashel007.quitsmoking.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.mainscreen.elements.NavRoutes
import ru.sashel007.quitsmoking.util.AppState
import ru.sashel007.quitsmoking.util.MySharedPref
import javax.inject.Inject

@HiltViewModel
class LastOpenedPageViewModel @Inject constructor(private val sharedPref: MySharedPref) :
    ViewModel() {

    private val _launchState = MutableStateFlow<AppState>(AppState.STARTING_PAGE)
    val launchState: StateFlow<AppState> = _launchState

    init {
        checkLastOpenedPage()
    }

    private fun checkLastOpenedPage() {
        viewModelScope.launch {
            when (sharedPref.getLastOpenedPage()) {
                NavRoutes.STARTING_PAGE -> _launchState.value = AppState.STARTING_PAGE
                NavRoutes.QUIT_DATE_SELECTION_PAGE -> _launchState.value = AppState.QUIT_DATE_SELECTION_PAGE
                NavRoutes.CIGARETTES_PER_DAY_PAGE -> _launchState.value = AppState.CIGARETTES_PER_DAY_PAGE
                NavRoutes.CIGARETTES_IN_PACK_PAGE -> _launchState.value = AppState.CIGARETTES_IN_PACK_PAGE
                NavRoutes.PACK_COST_PAGE -> _launchState.value = AppState.PACK_COST_PAGE
                NavRoutes.FIRST_MONTH_WITHOUT_SMOKING_PAGE -> _launchState.value = AppState.FIRST_MONTH_WITHOUT_SMOKING
                NavRoutes.NOTIFICATIONS_PAGE -> _launchState.value = AppState.NOTIFICATION_PAGE
                else -> _launchState.value = AppState.SUBSEQUENT_LAUNCH
            }
        }
    }
}