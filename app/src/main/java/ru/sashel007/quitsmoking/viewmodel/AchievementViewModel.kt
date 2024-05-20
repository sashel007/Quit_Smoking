package ru.sashel007.quitsmoking.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.data.repository.dto.mappers.AchievementMapper.toEntity
import ru.sashel007.quitsmoking.notification.AchievementWorker
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AchievementViewModel @Inject constructor(
    application: Application,
    private var repository: MyRepositoryImpl
) : AndroidViewModel(application) {
    private val _achievements = MutableLiveData<List<AchievementDto>>()
    val achievements: LiveData<List<AchievementDto>> = _achievements

    private val _newlyUnlockedAchievement = MutableLiveData<AchievementDto>()
    val newlyUnlockedAchievement: LiveData<AchievementDto> = _newlyUnlockedAchievement

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        startUpdatingAchievements()
        schedulePeriodicWork()
    }

    private fun startUpdatingAchievements() {
        coroutineScope.launch {
            while (true) {
                updateAchievementsProgress()
                delay(10_000)
            }
        }
    }

    private suspend fun updateAchievementsProgress() {
        try {
            val user = repository.getUserData()
            val quitTime = Instant.ofEpochMilli(user.quitTimeInMillisec)
            val currentTime = Instant.now()
            val timeSinceQuit = Duration.between(quitTime, currentTime).toMinutes()

            val currentAchievements = repository.getAllAchievements()
            val updatedAchievements = mutableListOf<AchievementDto>()

            for (achievement in currentAchievements) {
                val achievementDuration = achievement.duration
                val achievementProgressLine = achievement.progressLine
                val newProgressLine =
                    ((timeSinceQuit.toFloat() / achievementDuration) * 100).coerceIn(0f, 100f)
                val isNewlyUnlocked = newProgressLine >= 100 && !achievement.isUnlocked

                if (isNewlyUnlocked || newProgressLine != achievementProgressLine.toFloat()) {
                    val updatedAchievement = achievement.copy(
                        progressLine = newProgressLine.toInt(),
                        isUnlocked = isNewlyUnlocked
                    )
                    val achievementData = updatedAchievement.toEntity()
                    repository.updateAchievement(achievementData)
                    updatedAchievements.add(updatedAchievement)

                    if (isNewlyUnlocked) {
                        Log.d("Notification_check", "New achievement unlocked: ${updatedAchievement.name}")
                        _newlyUnlockedAchievement.postValue(updatedAchievement)
                    }
                } else {
                    updatedAchievements.add(achievement)
                }
            }
            _achievements.postValue(updatedAchievements)
        } catch (e: Exception) {
            Log.e("AchievementViewModel", "Error updating achievements", e)
        }
    }

    private fun schedulePeriodicWork() {
        val workRequest = PeriodicWorkRequestBuilder<AchievementWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "AchievementWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}