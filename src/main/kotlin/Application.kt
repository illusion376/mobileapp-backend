package io.illusion

import features.register.configureRegisterRouter
import features.login.configureLoginRouter
import io.illusion.data.database.DatabaseFactory
import io.illusion.data.database.initDatabase
import io.illusion.FirebaseSettings.FirebaseConfig
import io.illusion.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initDatabase()
    FirebaseConfig.init()
    configureSerialization()
    configureRouting()
    configureLoginRouter()
    configureRegisterRouter()
}