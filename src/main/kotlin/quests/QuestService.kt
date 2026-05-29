package quests

import quests.models.*
import kotlin.math.min

class QuestService(

    private val repository: QuestRepository
) {

    private val MAX_LEGIT_SPEED = 40.0

    fun processProgress(
        payload: QuestProgressPayload
    ) {
        // Проверка на слишком высокую скорость (Античит)
        if (payload.averageSpeedKmh >
            MAX_LEGIT_SPEED
        ) {
            return
        }

        val quests =
            repository.getPlayerQuests(
                payload.playerId
            )

        quests.forEach { pair ->

            val playerQuest = pair.first

            val template = pair.second

            if (playerQuest.completed) {
                return@forEach
            }

            val progress =
                calculateProgress(
                    template.objectiveType,
                    payload
                )

            repository.updateProgress(
                playerQuest.id,
                progress
            )

            if (progress >= template.targetValue) {

                repository.completeQuest(
                    playerQuest.id
                )
            }
        }
    }
    // Вычисляет текущий прогресс квеста
    private fun calculateProgress(
        type: QuestObjectiveType,
        payload: QuestProgressPayload
    ): Int {

        return when(type) {

            QuestObjectiveType
                .RUN_DISTANCE_WORKOUT -> {
                payload.workoutDistanceMeters
            }

            QuestObjectiveType
                .STEPS_WORKOUT -> {

                payload.workoutSteps
            }

            QuestObjectiveType
                .DISTANCE_WORKOUT -> {

                payload.workoutDistanceMeters
            }

            QuestObjectiveType
                .STREAK_DAYS -> {

                payload.streakDays
            }

            QuestObjectiveType
                .TOTAL_WORKOUTS -> {

                payload.totalWorkouts
            }

            QuestObjectiveType
                .TOTAL_DISTANCE -> {

                payload.totalDistanceMeters
            }

            QuestObjectiveType
                .COMPLETED_QUESTS -> {

                payload.completedQuests
            }
        }
    }

    fun claimReward(
        playerQuestId: Int
    ) {

        repository.claimQuest(
            playerQuestId
        )
    }
}