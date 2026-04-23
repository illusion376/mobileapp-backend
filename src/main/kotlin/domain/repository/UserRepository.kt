package domain.repository

import data.database.tables.Users
import data.database.tables.Users.passwordHash
import domain.models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

// Добавляет user в бд
fun createUser(email: String, password: String) {
    transaction {
        Users.insert {
            it[Users.email] = email
            it[Users.passwordHash] = password
        }
    }
}

// Возвращает user из бд
fun findUserByEmail(email: String): User? {
    return transaction {
        Users
            .select { Users.email eq email }
            .map {
                User(
                    id = it[Users.id],
                    email = it[Users.email],
                    password = it[passwordHash]
                )
            }
            .singleOrNull()
    }
}