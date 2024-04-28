package ru.sashel007.quitsmoking.data.repository.dto

data class CancelSmokingTips(
    val name: String,
    val text: String
)

data class TipsList(
    val tips: List<CancelSmokingTips>
)