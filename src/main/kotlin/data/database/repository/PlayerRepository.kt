package data.database.repository

import data.database.tables.ServerPlayers
import data.database.tables.Users
import domain.models.ServerPlayer
import domain.models.contracts.Player
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

/**
 * Применять строго внутри другой транзакции!
 * Создаёт нового персонажа пользователя и сохраняет его в базе данных.
 *
 * Выполняет вставку новой записи в таблицу персонажей пользователей
 * с указанными данными.
 *
 * @param userId уникальный идентификатор пользователя
 */
fun createServerPlayer(userId: Int) {
    val existingPlayer = findServerPlayerById(userId)
    if (existingPlayer != null) {
        throw Exception("Персонаж для пользователя с id $userId уже существует")
    }

    ServerPlayers.insert {
        it[ServerPlayers.id] = userId
        it[ServerPlayers.level] = 1
        it[ServerPlayers.experience] = 0
        it[ServerPlayers.strength] = 1
        it[ServerPlayers.stamina] = 1
        it[ServerPlayers.vitality] = 1
        it[ServerPlayers.steps] = 0
        it[ServerPlayers.streakDays] = 0
    }
}

/**
 * Ищет персонажа пользователя в базе данных по уникальному идентификатору.
 *
 * Выполняет запрос к таблице персонажей пользователей и возвращает
 * объект персонажа пользователя, если запись с указанным [userId] найдена.
 *
 * @param userId уникальный идентификатор для поиска
 * @return объект [ServerPlayer], если пользователь найден, иначе null
 */
fun findServerPlayerById(userId: Int): ServerPlayer? {
    return transaction {
        ServerPlayers
            .select { ServerPlayers.id eq userId }
            .map {
                ServerPlayer(
                    userId = it[ServerPlayers.id],
                    level = it[ServerPlayers.level],
                    experience = it[ServerPlayers.experience],
                    strength = it[ServerPlayers.strength],
                    stamina = it[ServerPlayers.stamina],
                    vitality = it[ServerPlayers.vitality],
                    steps = it[ServerPlayers.steps],
                    streakDays = it[ServerPlayers.streakDays]
                )
            }
            .singleOrNull()
    }
}

/**
 * Полностью обновляет все характеристики игрока в базе данных.
 *
 * Функция принимает общий интерфейс [Player] и синхронизирует все его поля
 * с записью в таблице [ServerPlayers] по уникальному идентификатору пользователя.
 *
 * @param player объект игрока, содержащий обновленные данные
 */
fun updateServerPlayer(player: Player) {
    transaction {
        ServerPlayers.update({ ServerPlayers.id eq player.userId }) {
            it[level] = player.level
            it[experience] = player.experience
            it[strength] = player.strength
            it[stamina] = player.stamina
            it[vitality] = player.vitality
            it[steps] = player.steps
            it[streakDays] = player.streakDays
        }
    }
}

/**
 * Выполняет модификацию данных игрока и сохраняет изменения.
 *
 * Переданный блок кода [block] принимает текущее состояние игрока, применяет изменения
 * и возвращает обновленный объект, который затем автоматически передается в [updateServerPlayer].
 *
 * @param player Исходный объект игрока для модификации.
 * @param block Лямбда-выражение, содержащее логику изменения полей.
 * @return Полностью обновленный и сохраненный объект игрока.
 */
fun updatePlayerField(player: Player, block: (Player) -> Player): Player {
    return block(player).also { updateServerPlayer(it) }
}