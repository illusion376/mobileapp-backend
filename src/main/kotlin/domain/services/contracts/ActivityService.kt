package domain.services.contracts

import domain.models.contracts.Player

interface ActivityService{
    fun addSteps(player: Player, steps : Int) : Player
}