package ru.sashel007.quitsmoking.data.repository.dto.mappers

import ru.sashel007.quitsmoking.data.db.entity.UserData
import ru.sashel007.quitsmoking.data.repository.dto.UserDto

object UserMapper{
    fun UserData.toDto(): UserDto =
        UserDto(
            quitTimeInMillisec = this.quitTimeInMillisec,
            cigarettesPerDay = this.cigarettesPerDay,
            cigarettesInPack = this.cigarettesInPack,
            packCost = this.packCost
        )

    fun List<UserData>.toDto(): List<UserDto> {
        return this.map { it.toDto() }
    }

    fun UserDto.toEntity(): UserData =
        UserData(
            quitTimeInMillisec = this.quitTimeInMillisec,
            cigarettesPerDay = this.cigarettesPerDay,
            cigarettesInPack = this.cigarettesInPack,
            packCost = this.packCost
        )
}