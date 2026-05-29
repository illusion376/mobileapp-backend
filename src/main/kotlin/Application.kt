package io.illusion

import features.register.configureRegisterRouter
import features.login.configureLoginRouter
import quests.configureQuestRouter
import data.database.initDatabase
import data.database.repository.findServerPlayerById
import domain.models.contracts.Player
import features.firebase.FirebaseConfig
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import plugins.configureRouting
import plugins.configureSerialization
import features.verification.configureVerificationRouter
import io.ktor.server.routing.routing
import quests.configureQuestRouter

fun main() {
    embeddedServer(CIO, port = 8080, host = "185.244.51.59", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(io.ktor.server.sse.SSE)
    initDatabase()
    FirebaseConfig.init()
    configureSerialization()
    configureRouting()
    configureLoginRouter()
    configureRegisterRouter()
    configureVerificationRouter()
    configureQuestRouter()
}