package domain.services

import data.database.repository.updatePlayerField
import domain.models.contracts.Player
import domain.services.contracts.LevelService
import domain.services.contracts.LevelService.Companion.calculateLevelAndExperience

class LevelServiceImpl : LevelService {

    // Таблица опыта: уровень -> суммарный опыт, необходимый для его достижения
    private val experienceRequired: Map<Int, Int> = mapOf(
        1 to 0,
        2 to 100,
        3 to 250,
        4 to 500,
        5 to 1000,
        6 to 2000,
        7 to 4000,
        8 to 8000,
        9 to 16000,
        10 to 32000
    )

    private val maxLevel: Int = experienceRequired.keys.maxOrNull() ?: 1

    override fun addExperience(player: Player, exp: Int): Player {
        return updatePlayerField(player) {
            calculateLevelAndExperience(player, player.experience + exp)
        }
    }
}
