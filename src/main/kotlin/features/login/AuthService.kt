package features.login

import features.firebase.FirebaseAuthResponse
import features.firebase.FirebaseClient
import features.firebase.FirebaseConfig
import features.firebase.LoginRequest
import features.firebase.LookupRequest
import features.firebase.UserInfoResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

object AuthService {
    suspend fun authenticate(receive: LoginReceive): Boolean {
        val key = FirebaseConfig.WEB_API_KEY

        val signInResponse = FirebaseClient.httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$key") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email = receive.email, password = receive.password))
        }

        if (signInResponse.status != HttpStatusCode.OK) throw Exception("INVALID_CREDENTIALS")

        val authData: FirebaseAuthResponse = signInResponse.body()
        val token = authData.idToken ?: return false

        val infoResponse = FirebaseClient.httpClient.post("https://identitytoolkit.googleapis.com/v1/accounts:lookup?key=$key") {
            contentType(ContentType.Application.Json)
            setBody(LookupRequest(idToken = token))
        }

        val userData: UserInfoResponse = infoResponse.body()
        return userData.users.firstOrNull()?.emailVerified ?: false
    }
}