package domain.services

import data.database.repository.findServerPlayerById
import domain.models.contracts.Player
import domain.services.contracts.LevelService
import domain.services.contracts.LevelService.Companion.calculateLevelAndExperience
import io.illusion.domain.services.contracts.PlayerService

class PlayerServiceImpl : PlayerService {
    override fun getPlayerProfile(userId: Int): Player? {
        val dbPlayer = findServerPlayerById(userId) ?: return null
        return recalculatePlayer(dbPlayer)
    }


    // нужно расширять
    private fun recalculatePlayer(player: Player) : Player{
        return calculateLevelAndExperience(player, player.experience)
    }
}