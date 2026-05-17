package domain.services

import data.database.repository.updatePlayerField
import domain.models.contracts.Player
import domain.enums.Attributes
import domain.services.contracts.AttributeService

class AttributeServiceImpl : AttributeService {
    override fun modifyAttribute(
        player: Player,
        amount: Int,
        attribute: Attributes
    ): Player {
        return updatePlayerField(player) { p ->
            return@updatePlayerField when (attribute) {
                Attributes.STRENGTH -> p.clone(strength = p.strength + amount)
                Attributes.STAMINA -> p.clone(stamina = p.stamina + amount)
                Attributes.VITALITY -> p.clone(vitality = p.vitality + amount)
            }
        }
    }
}