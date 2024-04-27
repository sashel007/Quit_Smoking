package ru.sashel007.quitsmoking.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.data.repository.dto.SmokingStats
import ru.sashel007.quitsmoking.data.repository.dto.UserDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SmokingStatsViewModel @Inject constructor(
    private var repository: MyRepositoryImpl
) : ViewModel() {
    private val _smokingStats = MutableLiveData(SmokingStats(0,0,0,0,0,0))
    val smokingStats: LiveData<SmokingStats?> = _smokingStats

    private val _showDialogEvent = MutableLiveData<Event<Unit>>()
    val showDialogEvent: LiveData<Event<Unit>> = _showDialogEvent

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    init {
        firstLoadData()
        loadDataAndLoopCalculation()
    }

    private fun firstLoadData() = viewModelScope.launch {
        val userDto = repository.getUserData(1)
        calculateSmokingStats(userDto)
    }

    private fun loadDataAndLoopCalculation() {
        coroutineScope.launch {
            while (true) {
                val userDto = repository.getUserData(1)
                calculateSmokingStats(userDto)
                delay(10000)
                Log.d("SmokingStats_Log", "val userDto = $userDto")
                Log.d("SmokingStats_Log", "_smokingStats.value = ${_smokingStats.value}")
            }
        }
    }

    private fun calculateSmokingStats(userDto: UserDto) {
        val localDateTime = setLocalDateTime(userDto)
        val currentDateTime = LocalDateTime.now()
        val time = countTimeForTimer(localDateTime, currentDateTime)
        val days = time[0]
        val cigarettesPerDay = userDto.cigarettesPerDay
        val nonSmokedCigarettes = if (days.toInt() != 0) {
            (cigarettesPerDay * days).toInt()
        } else {
            cigarettesPerDay
        }
        val packCost = userDto.packCost
        val cigarettesInPackage = userDto.cigarettesInPack
        val oneCigarettePrice = if (cigarettesInPackage != 0) {
            packCost / cigarettesInPackage
        } else {
            packCost / 1
        }
        val moneySaved = oneCigarettePrice * nonSmokedCigarettes
        val timeToSmoke = 7
        val timeSaved = timeToSmoke * nonSmokedCigarettes

        if (currentDateTime.isBefore(localDateTime)) {
            onShowDialog()
        }

        updateTimer(time)
        updateNonSmokedCigarettesStat(nonSmokedCigarettes)
        updateSavedMoneyStat(moneySaved)
        updateDaysSavedStat(timeSaved)
        Log.d(
            "SmokingStats_Log",
            "time = $time" +
                "nonSmokedCigarettes = $nonSmokedCigarettes" +
                "moneySaved = $moneySaved" +
                "timeSaved = $timeSaved"
        )
    }

    private fun setLocalDateTime(userDto: UserDto): LocalDateTime {
        val quitInstant = Instant.ofEpochMilli(userDto.quitTimeInMillisec)
        val zonedDateTime: ZonedDateTime = quitInstant.atZone(ZoneId.systemDefault())
        return zonedDateTime.toLocalDateTime()
    }

    private fun updateTimer(time: List<Long>) {
        val currentStats = _smokingStats.value
        val updatedStats = currentStats?.copy(
            days = time[0],
            hours = time[1],
            minutes = time[2]
        )
        Log.d("SmokingStats_Log", "updateTimer, currentStats = $currentStats, updatedStats = $updatedStats")
        _smokingStats.value = updatedStats
    }

    private fun countTimeForTimer(ldt: LocalDateTime, cdt: LocalDateTime): List<Long> {
        val difference = ldt.until(cdt, ChronoUnit.SECONDS)
        val days = difference / (24 * 60 * 60)
        val hours = (difference % (24 * 60 * 60)) / (60 * 60)
        val minutes = (difference % (60 * 60)) / 60
        return listOf(days, hours, minutes)
    }
    private fun updateNonSmokedCigarettesStat(nonSmokedCigs: Int) {
        val currentStats = _smokingStats.value
        val updatedStats = currentStats?.copy(nonSmokedCigarettes = nonSmokedCigs)
        Log.d("SmokingStats_Log", "updateNonSmokedCigarettesStat, currentStats = $currentStats, updatedStats = $updatedStats")
        _smokingStats.value = updatedStats
    }

    private fun updateSavedMoneyStat(moneySaved: Int) {
        val currentStats = _smokingStats.value
        val updatedStats = currentStats?.copy(savedMoney = moneySaved)
        Log.d("SmokingStats_Log", "updateSavedMoneyStat, currentStats = $currentStats, updatedStats = $updatedStats")
        _smokingStats.value = updatedStats
    }

    private fun updateDaysSavedStat(timeSaved: Int) {
        val currentStats = _smokingStats.value
        val updatedStats = currentStats?.copy(savedTimeInMinutes = timeSaved)
        Log.d("SmokingStats_Log", "updateDaysSavedStat, currentStats = $currentStats, updatedStats = $updatedStats")
        _smokingStats.value = updatedStats
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private fun onShowDialog() {
        _showDialogEvent.value = Event(Unit)
    }

    class Event<T>(private val content: T) {
        private var hasBeenHandled = false

        fun getContentIfNotHandled(): T? {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                content
            }
        }
    }
}