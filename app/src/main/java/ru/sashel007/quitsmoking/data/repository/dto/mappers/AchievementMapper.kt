package ru.sashel007.quitsmoking.data.repository.dto.mappers

import ru.sashel007.quitsmoking.data.db.entity.AchievementData
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto

object AchievementMapper {
    fun AchievementData.toDto(): AchievementDto {
        return AchievementDto(
            id = this.id,
            name = this.name,
            description = this.description,
            imageUri = this.imageUri,
            isUnlocked = this.isUnlocked,
            duration = this.duration,
            progressLine = this.progressLine
        )
    }

    fun AchievementDto.toEntity(): AchievementData {
        return AchievementData(
            id = this.id,
            name = this.name,
            description = this.description,
            imageUri = this.imageUri,
            isUnlocked = this.isUnlocked,
            duration = this.duration,
            progressLine = this.progressLine
        )
    }

    fun List<AchievementData>.toDto(): List<AchievementDto> {
        return this.map { it.toDto() }
    }

    fun List<AchievementDto>.toEntity(): List<AchievementData> {
        return this.map { it.toEntity() }
    }
}