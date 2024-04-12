package ru.sashel007.quitsmoking.data.repository.dto

import ru.sashel007.quitsmoking.data.db.entity.UserData

object UserMapper{
    fun UserData.toDto(): UserDto =
        UserDto(
            quitDate = this.quitDate,
            quitTime = this.quitTime,
            cigarettesPerDay = this.cigarettesPerDay,
            cigarettesInPack = this.cigarettesInPack,
            packCost = this.packCost
        )

    fun List<UserData>.toDto(): List<UserDto> {
        return this.map { it.toDto() }
    }

    fun UserDto.toEntity(): UserData =
        UserData(
            quitDate = this.quitDate,
            quitTime = this.quitTime,
            cigarettesPerDay = this.cigarettesPerDay,
            cigarettesInPack = this.cigarettesInPack,
            packCost = this.packCost
        )
}