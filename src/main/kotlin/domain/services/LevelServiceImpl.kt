package domain.services

import data.database.repository.updatePlayerField
import data.database.repository.updateServerPlayer
import domain.enums.LevelConfig
import domain.enums.LevelConfig.getExperienceForLevel
import domain.enums.LevelConfig.getLevelForExperience
import domain.models.ServerPlayer
import domain.models.contracts.Player
import domain.services.contracts.LevelService

class LevelServiceImpl : LevelService {
    override fun addExperience(player: Player, exp: Int): Player {
        return updatePlayerField(player) { p ->
            val updatePlayer = calculateLevelAndExperience(player, player.experience + exp)
            return@updatePlayerField updatePlayer
        }
    }

    private fun calculateLevelAndExperience(player: Player, totalExp: Int): Player {
        val predictionLevel = getLevelForExperience(totalExp)
        val currentLevel = player.level
        val finalLevel = maxOf(predictionLevel, currentLevel)
        val finalExperience = totalExp - getExperienceForLevel(finalLevel)
        return player.clone(experience = finalExperience, level = finalLevel)
    }
}