package io.illusion

import kotlinx.serialization.Serializable

@Serializable
data class User(val login: String, val password: String)

object UserStorage {
    val users = mutableListOf<User>()
}