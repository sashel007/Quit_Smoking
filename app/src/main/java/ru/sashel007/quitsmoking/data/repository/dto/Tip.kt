package ru.sashel007.quitsmoking.data.repository.dto

data class Tip(
    val id: Int,
    override val name: String,
    override val text: String
) : DialogSpecificType
