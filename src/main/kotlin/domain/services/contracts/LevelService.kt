package domain.services.contracts

import domain.models.ServerPlayer
import domain.models.contracts.Player

interface LevelService {
    /**
     * Добавляет опыт игроку.
     *
     * Пересчитывает текущий уровень на основе нового значения опыта
     * и сохраняет обновленные данные в базе.
     *
     * @param player Исходный объект игрока.
     * @param exp Количество добавляемого опыта.
     * @param return Обновленный объект игрока с новым уровнем и опытом.
     */
    fun addExperience(player : Player, exp : Int) : Player
}