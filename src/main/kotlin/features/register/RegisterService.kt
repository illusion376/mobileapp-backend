package features.register

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import io.illusion.Extensions.User
import io.illusion.FirebaseSettings.FirebaseClient
import io.illusion.FirebaseSettings.FirebaseConfig
import io.illusion.FirebaseSettings.FirebaseAuthResponse
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(val email: String, val password: String, val returnSecureToken: Boolean = true)

@Serializable
data class SendOobCodeRequest(val requestType: String, val idToken: String)

object RegisterService {

    suspend fun registerAndSendEmail(user: User) {
        try {
            val request = UserRecord.CreateRequest()
                .setEmail(user.login)
                .setPassword(user.password)
                .setEmailVerified(false)
            FirebaseAuth.getInstance().createUser(request)
        } catch (e: Exception) {
            println("User might already exist in Firebase Admin: ${e.message}")
        }

        val authUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=${FirebaseConfig.WEB_API_KEY}"

        val httpResponse = FirebaseClient.httpClient.post(authUrl) {
            contentType(ContentType.Application.Json)
            setBody(SignUpRequest(email = user.login, password = user.password))
        }

        if (httpResponse.status.isSuccess()) {
            val authResponse: FirebaseAuthResponse = httpResponse.body()
            val token = authResponse.idToken ?: throw Exception("ID Token is null")

            val emailResponse = FirebaseClient.httpClient.post(
                "${FirebaseConfig.AUTH_REST_URL}:sendOobCode?key=${FirebaseConfig.WEB_API_KEY}"
            ) {
                contentType(ContentType.Application.Json)
                setBody(SendOobCodeRequest(requestType = "VERIFY_EMAIL", idToken = token))
            }

            if (!emailResponse.status.isSuccess()) {
                throw Exception("Email error: ${emailResponse.bodyAsText()}")
            }


        } else {
            val errorBody = httpResponse.bodyAsText()
            throw Exception("Auth Error: $errorBody")
        }
    }
}