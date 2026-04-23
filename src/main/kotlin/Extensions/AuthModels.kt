package io.illusion.Extensions

import kotlinx.serialization.Serializable


@Serializable
data class AuthResponse(
    val status: Int,
    val message: String
)


@Serializable
data class User(
    val login: String,
    val password: String,
    val username: String? = null
)



