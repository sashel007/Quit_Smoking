package ru.sashel007.quitsmoking.data.repository.dto

data class UserDto(
    val quitTimeInMillisec: Long,
    val cigarettesPerDay: Int,
    val cigarettesInPack: Int,
    val packCost: Int
)
