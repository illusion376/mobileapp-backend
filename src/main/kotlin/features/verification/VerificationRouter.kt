package features.verification

import data.database.repository.verifyUserEmail
import helpers.FirebaseAdminService
import helpers.UserStatus
import io.ktor.server.application.*
import io.ktor.server.routing.*

import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.delay

fun Application.configureVerificationRouter() {
    routing {
        sse("/auth/status/stream") {
            val email = call.parameters["email"]

            if (email.isNullOrBlank()) {
                send(ServerSentEvent(data = "Error: Email is required", event = "error"))
                return@sse
            }

            while (true) {
                val userStatus = FirebaseAdminService.checkUserStatus(email.trim())

                when (userStatus) {
                    UserStatus.REGISTERED -> {
                        verifyUserEmail(email.trim())
                        send(ServerSentEvent(data = "true", event = "verified"))
                        return@sse
                    }
                    UserStatus.REQUIRES_CONFIRMATION -> {
                        send(ServerSentEvent(data = "false", event = "pending"))
                    }
                    UserStatus.NOT_FOUNDED -> {
                        send(ServerSentEvent(data = "User not found", event = "error"))
                        return@sse
                    }
                    else -> {
                        send(ServerSentEvent(data = "Server error", event = "error"))
                        return@sse
                    }
                }

                delay(5000)
            }
        }
    }
}
