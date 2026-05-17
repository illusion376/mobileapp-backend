package domain.services

import data.database.repository.updatePlayerField
import domain.models.contracts.Player
import domain.services.contracts.ActivityService

class ActivityServiceImpl : ActivityService {
    override fun addSteps(player: Player, steps: Int): Player {
        return updatePlayerField(player) { p ->
            return@updatePlayerField p.clone(steps = p.steps + steps)
        }
    }
}