package domain.enums

object LevelConfig {
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

    val maxLevel: Int = experienceRequired.keys.maxOrNull() ?: 1

    /**
     * Возвращает уровень, соответствующий текущему количеству опыта.
     *
     * Ищет максимальный уровень, планка опыта которого меньше или равна [currentXp].
     */
    fun getLevelForExperience(currentXp: Int): Int {
        return experienceRequired
            .filterValues { xpRequired -> currentXp >= xpRequired }
            .keys
            .maxOrNull() ?: 1
    }

    /**
     * Возвращает количество опыта, необходимое для достижения указанного уровня.
     */
    fun getExperienceForLevel(level: Int): Int {
        return experienceRequired[level.coerceIn(1, maxLevel)] ?: 0
    }
}