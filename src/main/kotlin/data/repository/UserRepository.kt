package io.illusion.data.repository

import io.illusion.data.database.tables.Users
import io.illusion.data.database.tables.Users.password
import io.illusion.data.repository.models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

fun createUser(email: String, password: String) {
    transaction {
        Users.insert {
            it[Users.email] = email
            it[Users.password] = password
        }
    }
}

fun findUserByEmail(email: String): User? {
    return transaction {
        Users
            .select { Users.email eq email }
            .map {
                User(
                    id = it[Users.id],
                    email = it[Users.email],
                    password = it[password]
                )
            }
            .singleOrNull()
    }
}