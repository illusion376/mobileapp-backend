package features.register

import extensions.AuthResponse
import extensions.isValidEmail
import data.database.repository.createUser
import data.database.repository.findUserByEmail
import helpers.PasswordHasher
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouter() {
    routing {
        post("/register") {
            val user = call.receive<RegisterReceive>()

            if (!user.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, AuthResponse(400, null,"Неверный формат почты"))
                return@post
            }

            val existingUser = findUserByEmail(user.email)
            if (existingUser != null) {
                call.respond(HttpStatusCode.Conflict, AuthResponse(409, null,"Пользователь уже с таким email существует"))
                return@post
            }

            try {
                RegisterService.registerAndSendEmail(user)

                val hashedPassword = PasswordHasher.hash(user.password)

                createUser(user.email, user.login, hashedPassword)

                call.respond(HttpStatusCode.Created, AuthResponse(201, null,"На ваш email было отправлено письмо с подтверждением регистрации"))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, AuthResponse(500, null, "Ошибка: ${e.message}"))
            }
        }
    }
}