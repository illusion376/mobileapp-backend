package io.illusion

import features.register.configureRegisterRouter
import features.login.configureLoginRouter
import io.illusion.plugins.configureRouting
import io.illusion.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureLoginRouter()
    configureRegisterRouter()
}
