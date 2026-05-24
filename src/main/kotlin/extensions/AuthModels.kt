package extensions

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val status: Int,
    val userId: Int?,
    val message: String,
    val token: String? = null
)




