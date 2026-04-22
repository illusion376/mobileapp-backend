package io.illusion.data.repository

import io.illusion.data.database.tables.Users
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

fun findUserByEmail(email: String): Pair<Int, String>? {
    return transaction {
        Users
            .select { Users.email eq email }
            .map { it[Users.id] to it[Users.email] }
            .singleOrNull()
    }
}