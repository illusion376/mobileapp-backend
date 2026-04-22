package io.illusion.data.repository

import io.illusion.data.database.tables.Users
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert

fun createUser(email: String, password: String) {
    transaction {
        Users.insert {
            it[Users.email] = email
            it[Users.password] = password
        }
    }
}