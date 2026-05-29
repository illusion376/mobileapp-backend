package domain.models

import domain.models.contracts.Player
import kotlinx.serialization.Serializable

@Serializable
data class ServerPlayer(
    override val userId: Int,
    override val level: Int,
    override val experience: Int,
    override val strength: Int,
    override val stamina: Int,
    override val vitality: Int,
    override val steps: Int,
    override val streakDays: Int
) : Player {
    override fun clone(
        userId: Int,
        level: Int,
        experience: Int,
        strength: Int,
        stamina: Int,
        vitality: Int,
        steps: Int,
        streakDays: Int
    ): Player {
        return this.copy(
            userId = userId,
            level = level,
            experience = experience,
            strength = strength,
            stamina = stamina,
            vitality = vitality,
            steps = steps,
            streakDays = streakDays
        )
    }
}