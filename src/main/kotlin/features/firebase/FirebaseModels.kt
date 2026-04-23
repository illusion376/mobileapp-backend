package features.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseAuthResponse(
    val idToken: String? = null,
    val email: String? = null
)

@Serializable
data class UserInfoResponse(
    val users: List<UserInfo>
)

@Serializable
data class UserInfo(
    val email: String,
    val emailVerified: Boolean
)

@Serializable
data class AuthResponse(
    val status: Int,
    val message: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)

@Serializable
data class LookupRequest(
    val idToken: String
)