package io.illusion.data.database.mapper

import domain.enums.LevelConfig.getExperienceForLevel
import domain.models.contracts.Player
import io.illusion.data.database.models.PlayerResponce

fun Player.toData() : PlayerResponce{
    return PlayerResponce(
        userId = this.userId,
        level = this.level,
        experience = this.experience,
        experienceToNextLevel = getExperienceForLevel(this.level + 1),
        strength = this.strength,
        stamina = this.stamina,
        vitality = this.vitality,
        steps = this.steps,
        streakDays = this.streakDays
    )
}