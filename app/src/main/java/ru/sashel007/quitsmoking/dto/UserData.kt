package ru.sashel007.quitsmoking.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quitDate: Long,
    val quitTime: Int,
    val cigarettesPerDay: Int,
    val cigarettesInPack: Int,
    val packCost: Double
)
