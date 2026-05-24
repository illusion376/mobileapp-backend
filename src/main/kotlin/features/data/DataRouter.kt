package io.illusion.features.data

import data.database.repository.findServerPlayerById
import io.illusion.data.database.mapper.toData
import io.illusion.domain.services.contracts.PlayerService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureDataRouter() {
    val playerService by inject<PlayerService>()
    routing {
        authenticate("auth-jwt") {
            get("/character/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()

                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing userId parameter")
                    return@get
                }

                val character = playerService.getPlayerProfile(userId)
                if (character != null) {
                    call.respond(character.toData())
                } else {
                    call.respond(HttpStatusCode.NotFound, "Character not found")
                }

            }
        }
    }
}