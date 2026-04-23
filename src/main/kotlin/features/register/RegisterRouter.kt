package features.register

import io.illusion.Extensions.AuthResponse
import io.illusion.Extensions.User
import io.illusion.Extensions.isValidEmail
import io.illusion.data.repository.createUser
import io.illusion.data.repository.findUserByEmail
import io.illusion.helpers.PasswordHasher
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouter() {
    routing {
        post("/register") {
            val user = call.receive<User>()

            if (!user.login.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, AuthResponse(400, "Неверный формат почты"))
                return@post
            }

            val existingUser = findUserByEmail(user.login)
            if (existingUser != null) {
                call.respond(HttpStatusCode.Conflict, AuthResponse(409, "Пользователь уже с таким email существует"))
                return@post
            }

            try {
                RegisterService.registerAndSendEmail(user)

                val hashedPassword = PasswordHasher.hash(user.password)

                createUser(user.login, hashedPassword)

                call.respond(HttpStatusCode.Created, AuthResponse(201, "На ваш email было отправлено письмо с подтверждением регистрации"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, AuthResponse(500, "Ошибка: ${e.message}"))
            }
        }
    }
}