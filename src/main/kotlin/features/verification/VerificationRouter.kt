package features.verification

import helpers.FirebaseAdminService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class VerificationResponse(
    val isVerified: Boolean
)

fun Application.configureVerificationRouter() {
    routing {
        get("/auth/status") {
            val email = call.parameters["email"]

            if (email.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, VerificationResponse(false))
                return@get
            }

            val userStatus = FirebaseAdminService.checkUserStatus(email.trim())

            when (userStatus) {
                // 200 OK и true
                "зарегистрирован" -> {
                    call.respond(HttpStatusCode.OK, VerificationResponse(true))
                }
                "требует подтверждения" -> {
                    //  403 Forbidden и false
                    call.respond(HttpStatusCode.Forbidden, VerificationResponse(false))
                }
                "не найден" -> {
                    // 404 Not Found и false
                    call.respond(HttpStatusCode.NotFound, VerificationResponse(false))
                }
                else -> {
                    // 500 Internal Server Error и false
                    call.respond(HttpStatusCode.InternalServerError, VerificationResponse(false))
                }
            }
        }
    }
}