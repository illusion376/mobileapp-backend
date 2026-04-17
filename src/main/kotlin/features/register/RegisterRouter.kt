package features.register

import features.login.LoginReceive
import io.illusion.UserStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRegisterRouter() {
    routing {
        post("/register") {
            val receiveUser = call.receive<LoginReceive>()
            val isExist = UserStorage.users.any { it.login == receiveUser.login }

            if (isExist) {
                call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
            } else {
                UserStorage.users.add(receiveUser)
                call.respond(HttpStatusCode.Created, "Вы успешно зарегистрировались")
            }
        }
    }
}