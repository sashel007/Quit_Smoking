package ru.sashel007.quitsmoking.data.repository.dto

import ru.sashel007.quitsmoking.data.db.entity.UserData

class UserMapper {
    fun UserData.toDto(): UserDto =
        UserDto(
            quitDate = this.quitDate,
            quitTime = this.quitTime,
            cigarettesPerDay = this.cigarettesPerDay,
            cigarettesInPack = this.cigarettesInPack,
            packCost = this.packCost
        )

    fun UserDto.toEntity(): UserData =
        UserData(
            quitDate = this.quitDate,
            quitTime = this.quitTime,
            cigarettesPerDay = this.cigarettesPerDay,
            cigarettesInPack = this.cigarettesInPack,
            packCost = this.packCost
        )
}