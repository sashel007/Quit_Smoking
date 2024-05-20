package ru.sashel007.quitsmoking.viewmodel

import android.util.Log
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

@HiltViewModel
class SmokingStatsViewModel @Inject constructor(
    private var repository: MyRepositoryImpl
) : ViewModel() {
    private val _smokingStats = MutableLiveData(SmokingStats(0, 0, 0, 0, 0, 0))
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
        val userDto = repository.getUserData()
        calculateSmokingStats(userDto)
    }

    private fun loadDataAndLoopCalculation() {
        coroutineScope.launch {
            while (true) {
                val userDto = repository.getUserData()
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
        val hours = time[1]
        val minutes = time[2]
        val cigarettesPerDay = userDto.cigarettesPerDay

        // Расчет доли дня
        val dayFraction = (hours.toDouble() / 24) + (minutes.toDouble() / 1440) // 1440 - это количество минут в сутках
        val nonSmokedCigarettes = (days * cigarettesPerDay + (dayFraction * cigarettesPerDay)).toInt()

        val packCost = userDto.packCost
        val cigarettesInPackage = userDto.cigarettesInPack

        // Calculate the cost of one cigarette
        val oneCigarettePrice = packCost.toDouble() / maxOf(1, cigarettesInPackage)
        // Calculate the total money saved based on the number of unsmoked cigarettes
        val moneySaved = (oneCigarettePrice * nonSmokedCigarettes).toInt()

        val timeToSmokePerCigarette = 7
        val timeSavedInMinutes = timeToSmokePerCigarette * nonSmokedCigarettes

        if (currentDateTime.isBefore(localDateTime)) {
            onShowDialog()
        }

        val exactMoneySaved = calculateExactMoneySaved(oneCigarettePrice, dayFraction, cigarettesPerDay)
        Log.d("SmokingStats_Log", "exactMoneySaved = $exactMoneySaved")

        updateTimer(time)
        updateNonSmokedCigarettesStat(nonSmokedCigarettes)
        updateSavedMoneyStat(moneySaved)
        updateDaysSavedStat(timeSavedInMinutes)

        val formattedTimeSaved = formatTimeSaved(timeSavedInMinutes)

        Log.d(
            "SmokingStats_Log",
            "time = $time, nonSmokedCigarettes = $nonSmokedCigarettes, moneySaved = $moneySaved, timeSaved = $timeSavedInMinutes"
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
        Log.d(
            "SmokingStats_Log",
            "updateTimer, currentStats = $currentStats, updatedStats = $updatedStats"
        )
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
        Log.d(
            "SmokingStats_Log",
            "updateNonSmokedCigarettesStat, currentStats = $currentStats, updatedStats = $updatedStats"
        )
        _smokingStats.value = updatedStats
    }

    private fun updateSavedMoneyStat(moneySaved: Int) {
        val currentStats = _smokingStats.value
        val updatedStats = currentStats?.copy(savedMoney = moneySaved)
        Log.d(
            "SmokingStats_Log",
            "updateSavedMoneyStat, currentStats = $currentStats, updatedStats = $updatedStats"
        )
        _smokingStats.value = updatedStats
    }

    private fun updateDaysSavedStat(timeSaved: Int) {
        val currentStats = _smokingStats.value
        val updatedStats = currentStats?.copy(savedTimeInMinutes = timeSaved)
        Log.d(
            "SmokingStats_Log",
            "updateDaysSavedStat, currentStats = $currentStats, updatedStats = $updatedStats"
        )
        _smokingStats.value = updatedStats
    }

    private fun calculateExactMoneySaved(
        oneCigarettePrice: Double,
        dayFraction: Double,
        cigarettesPerDay: Int
    ): Double {
        val cigarettesNotSmokedToday = dayFraction * cigarettesPerDay
        val exactMoneySaved = oneCigarettePrice * cigarettesNotSmokedToday
        Log.d("SmokingStats_Log", "oneCigarettePrice: $oneCigarettePrice, dayFraction: $dayFraction, cigarettesPerDay: $cigarettesPerDay, cigarettesNotSmokedToday: $cigarettesNotSmokedToday, exactMoneySaved: $exactMoneySaved")
        return exactMoneySaved
    }


    private fun formatTimeSaved(totalMinutes: Int): String {
        val days = totalMinutes / 1440
        val hours = (totalMinutes % 1440) / 60
        val minutes = totalMinutes % 60

        return when {
            days >= 30 -> "${days / 30} мес."
            days >= 7 -> "${days / 7} нед."
            days > 0 -> "$days д."
            hours > 0 -> "$hours час"
            else -> "$minutes мин."
        }
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