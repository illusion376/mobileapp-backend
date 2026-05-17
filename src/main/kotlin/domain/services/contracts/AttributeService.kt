package domain.services.contracts

import domain.enums.Attributes
import domain.models.contracts.Player

interface AttributeService {
    /**
     * Изменяет значение указанной характеристики игрока.
     *
     * Метод может как увеличивать характеристику (при положительном [amount]),
     * так и уменьшать её (при отрицательном [amount]).
     *
     * @param player Исходный объект игрока.
     * @param amount Значение, на которое изменится характеристика.
     * @param attribute Тип изменяемой характеристики из перечисления [Attributes].
     * @return Обновленный объект игрока с измененной характеристикой.
     */
    fun modifyAttribute(player: Player, amount : Int, attribute : Attributes) : Player
}