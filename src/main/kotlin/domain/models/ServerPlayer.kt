package domain.models

import domain.models.contracts.Player

data class ServerPlayer(
    override val userId: Int,
    override val level: Int,
    override val experience: Int,
    override val strength: Int,
    override val stamina: Int,
    override val vitality: Int,
    override val steps: Int
) : Player {
    override fun clone(
        userId: Int,
        level: Int,
        experience: Int,
        strength: Int,
        stamina: Int,
        vitality: Int,
        steps: Int
    ): Player {
        return this.copy(
            userId = userId,
            level = level,
            experience = experience,
            strength = strength,
            stamina = stamina,
            vitality = vitality,
            steps = steps
        )
    }
}