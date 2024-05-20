package ru.sashel007.quitsmoking.data.repository.dto

data class AchievementDto(
    val id: Int,
    val name: String,
    val description: String,
    val imageUri: String,
    val isUnlocked: Boolean,
    val duration: Int,
    val progressLine: Int
)