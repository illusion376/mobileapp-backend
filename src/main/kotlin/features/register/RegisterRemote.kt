package features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRemote(
    val login: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val token: String
)