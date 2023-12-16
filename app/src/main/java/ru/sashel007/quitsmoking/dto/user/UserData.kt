package ru.sashel007.quitsmoking.dto.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val quitDate: Long,
    val quitTime: Int,
    val cigarettesPerDay: Int,
    val cigarettesInPack: Int,
    val packCost: Int
)
