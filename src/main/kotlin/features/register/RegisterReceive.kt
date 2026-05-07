package features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceive(
    val email: String,
    val login: String,
    val password: String
)