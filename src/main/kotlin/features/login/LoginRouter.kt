package features.login

import io.illusion.UserStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureLoginRouter() {
    routing {
        post("/login") {
            val receiveUser = call.receive<LoginReceive>()
            val userInStorage = UserStorage.users.find {
                it.login == receiveUser.login && it.password == receiveUser.password
            }

            if (userInStorage != null) {
                call.respond(HttpStatusCode.OK, "Вход выполнен успешно")
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Неверный логин или пароль")
            }
        }
    }
}