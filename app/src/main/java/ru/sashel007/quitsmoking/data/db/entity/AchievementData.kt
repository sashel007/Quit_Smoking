package ru.sashel007.quitsmoking.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementData(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUri: String,
    val isUnlocked: Boolean
)