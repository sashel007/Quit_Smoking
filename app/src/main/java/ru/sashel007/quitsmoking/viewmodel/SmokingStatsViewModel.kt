package ru.sashel007.quitsmoking.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val _smokingStats = MutableLiveData<SmokingStats?>()
    val smokingStats: LiveData<SmokingStats?> = _smokingStats

    private val _showDialogEvent = MutableLiveData<Event<Unit>>()
    val showDialogEvent: LiveData<Event<Unit>> = _showDialogEvent

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(job + Dispatchers.IO)

    init {
        loadDataAndLoopCalculation()
    }

    private fun loadDataAndLoopCalculation() {
        coroutineScope.launch {
            val userDto = repository.getUserData(1)
            while (true) {
                delay(10000)
                calculateSmokingStats(userDto)
                Log.d("SmokingStats_Log_UserDto", userDto.toString())
                Log.d("SmokingStats_Log_smokingStats.value", _smokingStats.value.toString())
            }
        }
    }

    private fun calculateSmokingStats(userDto: UserDto) {
        val localDateTime = setLocalDateTime(userDto)
        val currentDateTime = LocalDateTime.now()
        val time = countTimeForTimer(localDateTime, currentDateTime)
        val days = time[0]
        val nonSmokedCigarettes = (userDto.cigarettesPerDay * days).toInt()
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
    }

    private fun setLocalDateTime(userDto: UserDto): LocalDateTime {
        val quitInstant = Instant.ofEpochMilli(userDto.quitTimeInMillisec)
        val zonedDateTime: ZonedDateTime = quitInstant.atZone(ZoneId.systemDefault())
        return zonedDateTime.toLocalDateTime()
    }

    private fun updateTimer(time: List<Long>) {
        val currentStats = _smokingStats.value ?: SmokingStats(0, 0, 0, 0, 0, 0)
        val updatedStats = currentStats.copy(
            days = time[0],
            hours = time[1],
            minutes = time[2]
        )
        _smokingStats.postValue(updatedStats)
    }

    private fun countTimeForTimer(ldt: LocalDateTime, cdt: LocalDateTime): List<Long> {
        val difference = ldt.until(cdt, ChronoUnit.SECONDS)
        val days = difference / (24 * 60 * 60)
        val hours = (difference % (24 * 60 * 60)) / (60 * 60)
        val minutes = (difference % (60 * 60)) / 60
        return listOf(days, hours, minutes)
    }
    private fun updateNonSmokedCigarettesStat(nonSmokedCigs: Int) {
        val currentStats = _smokingStats.value ?: SmokingStats(0, 0, 0, 0, 0, 0)
        val updatedStats = currentStats.copy(nonSmokedCigarettes = nonSmokedCigs)
        _smokingStats.postValue(updatedStats)
    }

    private fun updateSavedMoneyStat(moneySaved: Int) {
        val currentStats = _smokingStats.value ?: SmokingStats(0, 0, 0, 0, 0, 0)
        val updatedStats = currentStats.copy(savedMoney = moneySaved)
        _smokingStats.postValue(updatedStats)
    }

    private fun updateDaysSavedStat(timeSaved: Int) {
        val currentStats = _smokingStats.value ?: SmokingStats(0, 0, 0, 0, 0, 0)
        val updatedStats = currentStats.copy(savedTimeInMinutes = timeSaved)
        _smokingStats.postValue(updatedStats)
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