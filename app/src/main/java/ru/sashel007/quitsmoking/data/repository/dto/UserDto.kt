package ru.sashel007.quitsmoking.data.repository.dto

data class UserDto(
    val quitDate: Long,
    val quitTime: Int,
    val cigarettesPerDay: Int,
    val cigarettesInPack: Int,
    val packCost: Int
)
