package ru.sashel007.quitsmoking.data.repository.dto

data class AchievementDto(
    val name: String,
    val description: String,
    val imageUri: String,
    val isUnlocked: Boolean
)
