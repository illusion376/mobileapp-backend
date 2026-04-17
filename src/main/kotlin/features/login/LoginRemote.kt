package features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceive(
    val login:String,
    val password:String
)

@Serializable
data class LoginResponse(
    val token: String
)