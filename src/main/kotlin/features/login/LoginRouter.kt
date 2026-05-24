package features.login

import data.database.repository.findUserByEmail
import data.database.repository.isUserVerified
import extensions.AuthResponse
import helpers.PasswordHasher
import io.illusion.helpers.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureLoginRouter() {
    routing {
        post("/login") {
            val receive = call.receive<LoginReceive>()

            try {
                val userFromDb = findUserByEmail(receive.email)
                if (userFromDb == null) {
                    call.respond(HttpStatusCode.NotFound, AuthResponse(404, null, "Пользователь не найден"))
                    return@post
                }

                val isPasswordCorrect = PasswordHasher.verify(
                    password = receive.password,
                    hash = userFromDb.password
                )

                if (!isPasswordCorrect) {
                    call.respond(HttpStatusCode.Unauthorized, AuthResponse(401, null, "Неверный логин или пароль"))
                    return@post
                }

                val isVerified = AuthService.authenticate(receive) && isUserVerified(receive.email)

                if (isVerified) {
                    val token = TokenService.generateToken(userFromDb.email)
                    val userId = userFromDb.id

                    call.respond(
                        HttpStatusCode.OK,
                        AuthResponse(200, userId, "Вход выполнен успешно", token)
                    )
                } else {
                    call.respond(HttpStatusCode.Forbidden, AuthResponse(403, null, "Почта не подтверждена"))
                }

            } catch (e: Exception) {
                val message = e.message ?: "Unknown error"
                if (message.contains("INVALID_CREDENTIALS") || message.contains("EMAIL_NOT_FOUND")) {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        AuthResponse(401, null, "Ошибка авторизации в Firebase")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        AuthResponse(500, null, "Ошибка сервера: $message")
                    )
                }
            }
        }
    }
}