package features.login

import io.illusion.Extensions.AuthResponse
import io.illusion.data.repository.findUserByEmail
import io.illusion.helpers.PasswordHasher
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureLoginRouter() {
    routing {
        post("/login") {
            val receive = call.receive<LoginReceive>()

            try {
                val userFromDb = findUserByEmail(receive.login)

                if (userFromDb == null) {
                    call.respond(HttpStatusCode.NotFound, AuthResponse(404, "Пользователь не найден"))
                    return@post
                }

                val isPasswordCorrect = PasswordHasher.verify(
                    password = receive.password,
                    hash = userFromDb.password
                )

                if (!isPasswordCorrect) {
                    call.respond(HttpStatusCode.Unauthorized, AuthResponse(401, "Неверный логин или пароль"))
                    return@post
                }

                val isVerified = LoginService.authenticate(receive)

                if (isVerified) {
                    call.respond(HttpStatusCode.OK, AuthResponse(200, "Вход выполнен успешно"))
                } else {
                    call.respond(HttpStatusCode.Forbidden, AuthResponse(403, "Почта не подтверждена"))
                }

            } catch (e: Exception) {
                val message = e.message ?: "Unknown error"
                if (message.contains("INVALID_CREDENTIALS") || message.contains("EMAIL_NOT_FOUND")) {
                    call.respond(HttpStatusCode.Unauthorized, AuthResponse(401, "Ошибка авторизации в Firebase"))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, AuthResponse(500, "Ошибка сервера: $message"))
                }
            }
        }
    }
}