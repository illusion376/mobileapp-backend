package domain.services.contracts

import domain.models.contracts.Player

interface LevelService {

    /**
     * Добавляет опыт игроку.
     *
     * Метод увеличивает общий опыт персонажа, автоматически пересчитывает
     * текущий уровень и остаток опыта для этого уровня, а затем
     * сохраняет изменения в репозитории.
     *
     * @param player Исходный объект игрока до начисления опыта.
     * @param exp Количество добавляемого опыта (должно быть положительным).
     * @return Обновленный объект игрока с актуальным уровнем и опытом.
     */
    fun addExperience(player: Player, exp: Int): Player

    companion object {
        private val experienceRequired: Map<Int, Int> = mapOf(
            1 to 0, 2 to 100, 3 to 250, 4 to 500, 5 to 1000,
            6 to 2000, 7 to 4000, 8 to 8000, 9 to 16000, 10 to 32000
        )

        private val maxLevel: Int = experienceRequired.keys.maxOrNull() ?: 1

        /**
         * Вычисляет итоговый уровень и чистый остаток опыта для текущего уровня.
         */
        fun calculateLevelAndExperience(player: Player, totalExp: Int): Player {
            val predictionLevel = getLevelForExperience(totalExp)
            val currentLevel = player.level

            val finalLevel = maxOf(predictionLevel, currentLevel)

            val calculatedXp = totalExp - getExperienceForLevel(finalLevel)
            val finalExperience = maxOf(0, calculatedXp)

            return player.clone(experience = finalExperience, level = finalLevel)
        }

        /**
         * Возвращает уровень, соответствующий текущему количеству опыта.
         */
        fun getLevelForExperience(currentXp: Int): Int {
            val calculatedLevel = experienceRequired.count { (_, xpRequired) -> currentXp >= xpRequired }
            return calculatedLevel.coerceIn(1, maxLevel)
        }

        /**
         * Возвращает количество опыта, необходимое для достижения указанного уровня.
         */
        fun getExperienceForLevel(level: Int): Int {
            return experienceRequired[level.coerceIn(1, maxLevel)] ?: 0
        }
    }
}
