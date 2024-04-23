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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.data.repository.MyRepositoryImpl
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.data.repository.dto.mappers.AchievementMapper.toEntity
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AchievementViewModel @Inject constructor(
    private var repository: MyRepositoryImpl
) : ViewModel() {
    private val _achievements = MutableLiveData<List<AchievementDto>>()
    val achievements: LiveData<List<AchievementDto>> = _achievements

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        startUpdatingAchievements()
    }

    private fun startUpdatingAchievements() {
        coroutineScope.launch {
            while (true) {
                delay(10_000)
                updateAchievementsProgress()
            }
        }
    }

    private suspend fun updateAchievementsProgress() {
        try {
            val user = repository.getUserData(1)
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
                } else {
                    updatedAchievements.add(achievement)
                }
            }
            _achievements.postValue(updatedAchievements)
        } catch (e: Exception) {
            Log.e("AchievementViewModel", "Error updating achievements", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}