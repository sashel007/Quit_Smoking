package ru.sashel007.quitsmoking.data.repository.dto

data class Fact(
    val id: Int,
    override val name: String,
    override val text: String
) : DialogSpecificType
