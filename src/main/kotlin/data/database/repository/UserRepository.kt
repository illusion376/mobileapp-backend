package data.database.repository

import data.database.tables.Users
import data.database.tables.Users.passwordHash
import domain.models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

/**
 * Создаёт нового пользователя и сохраняет его в базе данных.
 *
 * Выполняет вставку новой записи в таблицу пользователей
 * с указанными данными.
 *
 * @param email адрес электронной почты пользователя
 * @param login логин пользователя
 * @param password хэшированный пароль пользователя
 */
fun createUser(email: String, login: String, password: String) {
    transaction {
        val newUserId = Users.insert {
            it[Users.email] = email
            it[Users.login] = login
            it[Users.passwordHash] = password
            it[Users.isVerified] = false
        } get Users.id
        createServerPlayer(newUserId)
    }
}

/**
 * Ищет пользователя в базе данных по адресу электронной почты.
 *
 * Выполняет запрос к таблице пользователей и возвращает
 * объект пользователя, если запись с указанным email найдена.
 *
 * @param email адрес электронной почты пользователя для поиска
 * @return объект [User], если пользователь найден, иначе null
 */
fun findUserByEmail(email: String): User? {
    return transaction {
        Users
            .select { Users.email eq email }
            .map {
                User(
                    id = it[Users.id],
                    login = it[Users.login],
                    email = it[Users.email],
                    password = it[passwordHash]
                )
            }
            .singleOrNull()
    }
}

/**
 * Ищет пользователя в базе данных по уникальному идентификатору.
 *
 * Выполняет запрос к таблице пользователей и возвращает
 * объект пользователя, если запись с указанным email найдена.
 *
 * @param id уникальный идентификатор для поиска
 * @return объект [User], если пользователь найден, иначе null
 */
fun findUserById(id: Int): User? {
    return transaction {
        Users
            .select { Users.id eq id }
            .map {
                User(
                    id = it[Users.id],
                    login = it[Users.login],
                    email = it[Users.email],
                    password = it[passwordHash]
                )
            }
            .singleOrNull()
    }
}

/**
 * Проверяет статус верификации пользователя в базе данных по адресу электронной почты.
 *
 * Выполняет запрос к таблице пользователей и возвращает
 * статус пользователя, если запись с указанным email найдена.
 *
 * @param email адрес электронной почты пользователя для поиска
 * @return true, если пользователь верифицирован, иначе false
 */
fun isUserVerified(email: String): Boolean {
    return transaction {
        Users.select { Users.email eq email }
            .map { it[Users.isVerified] }
            .singleOrNull() ?: false
    }
}

/**
 * Меняет статус верификации пользователя в базе данных по адресу электронной почты.
 */
fun verifyUserEmail(email: String): Boolean {
    return transaction {
        val updatedRows = Users.update({ Users.email eq email }) {
            it[Users.isVerified] = true
        }
        updatedRows > 0
    }
}