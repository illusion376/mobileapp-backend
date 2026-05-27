package quests

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import quests.models.QuestProgressPayload

fun Route.questRoutes() {

    val service =
        QuestService(
            QuestRepository()
        )

    route("/quests") {

        get("/test") {

            call.respond(
                "QUEST ROUTE WORKS"
            )
        }

        post("/claim/{questId}") {

            val questId =
                call.parameters["questId"]!!
                    .toInt()

            service.claimReward(
                questId
            )

            call.respond(
                "Quest claimed"
            )
        }

        post("/test-progress") {

            val payload =
                QuestProgressPayload(
                    playerId = 22,
                    workoutSteps = 8000,
                    workoutDistanceMeters = 4000,
                    averageSpeedKmh = 12.0,
                    streakDays = 5,
                    totalWorkouts = 10,
                    totalDistanceMeters = 120000,
                    completedQuests = 3
                )

            service.processProgress(payload)

            call.respond("processed")
        }
    }
}