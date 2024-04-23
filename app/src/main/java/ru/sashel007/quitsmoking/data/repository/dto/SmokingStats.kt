package ru.sashel007.quitsmoking.data.repository.dto

data class SmokingStats(
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val nonSmokedCigarettes: Int,
    val savedMoney: Int,
    val savedTimeInMinutes: Int
)
