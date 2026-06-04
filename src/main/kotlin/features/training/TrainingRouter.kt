package io.illusion.features.training

import data.database.repository.findServerPlayerById
import data.database.repository.updateServerPlayer
import domain.services.contracts.LevelService
import io.illusion.features.training.dto.TrainingSessionDTO
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.math.roundToInt

fun Application.configureTrainingRouter() {
    val levelService by inject<LevelService>()

    routing {
        authenticate("auth-jwt") {
            post("/training/finish/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()

                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Missing userId parameter")
                    return@post
                }

                val sessionData = try {
                    call.receive<TrainingSessionDTO>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid data format")
                    return@post
                }

                val player = findServerPlayerById(userId)
                if (player == null) {
                    call.respond(HttpStatusCode.NotFound, "Character not found")
                    return@post
                }
                // Вычисляем опыт на основе данных тренировки
                val stepsXp = sessionData.steps * 0.05
                val distanceXp = sessionData.distanceMeters * 0.01
                val durationXp = sessionData.durationMinutes * 0.6

                val totalXpEarned = (stepsXp + distanceXp + durationXp).roundToInt()

                // Если опыт не заработан, просто обновляем шаги и возвращаем 0 опыта
                if (totalXpEarned <= 0) {
                    val playerWithOnlySteps = player.clone(
                        steps = player.steps + sessionData.steps
                    )
                    updateServerPlayer(playerWithOnlySteps)

                    call.respond(HttpStatusCode.OK, mapOf("xpEarned" to 0))
                    return@post
                }


                val playerWithNewXp = levelService.addExperience(player, totalXpEarned)


                val finalUpdatedPlayer = playerWithNewXp.clone(
                    steps = playerWithNewXp.steps + sessionData.steps
                )


                updateServerPlayer(finalUpdatedPlayer)

                call.respond(HttpStatusCode.OK, mapOf("xpEarned" to totalXpEarned))
            }
        }
    }
}