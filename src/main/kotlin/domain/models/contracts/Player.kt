package domain.models.contracts

interface Player {
    val userId: Int
    val level: Int
    val experience: Int
    val strength : Int
    val stamina : Int
    val vitality : Int
    val steps : Int
    fun clone(
        userId: Int = this.userId,
        level: Int = this.level,
        experience: Int = this.experience,
        strength: Int = this.strength,
        stamina: Int = this.stamina,
        vitality: Int = this.vitality,
        steps: Int = this.steps
    ): Player
}