package ru.sashel007.quitsmoking.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class AchievementData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val imageUri: String,
    val isUnlocked: Boolean,
    val duration: Int,
    val progressLine: Int = 0
)