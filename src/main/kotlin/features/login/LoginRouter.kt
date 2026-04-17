package features.login

import io.ktor.server.application.Application
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureLoginRouter() {
    routing {
        post("/login") {
            call.respondText { "Hello World login" }
        }
    }
}
