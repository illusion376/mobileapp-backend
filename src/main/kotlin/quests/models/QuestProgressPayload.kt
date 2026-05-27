package quests.models

data class QuestProgressPayload(
    val playerId: Int,
    val workoutSteps: Int,
    val workoutDistanceMeters: Int,
    val averageSpeedKmh: Double,
    val streakDays: Int,
    val totalWorkouts: Int,
    val totalDistanceMeters: Int,
    val completedQuests: Int
)