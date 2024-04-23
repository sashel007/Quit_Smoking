package ru.sashel007.quitsmoking.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserData(
    @PrimaryKey
    val id: Int = 1,
    val quitTimeInMillisec: Long,
    val cigarettesPerDay: Int,
    val cigarettesInPack: Int,
    val packCost: Int
)
