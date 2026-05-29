package data.database.tables

import org.jetbrains.exposed.sql.Table
import quests.models.QuestCategory
import quests.models.QuestObjectiveType


object WorkoutSessions : Table("workout_sessions") {

    val id = integer("id")
            .autoIncrement()
    val userId = integer("user_id")
            .references(Users.id)
    val startedAt = long("started_at")
    val endedAt = long("ended_at")
    val durationSeconds = integer("duration_seconds")
    val steps = integer("steps")
    val distanceMeters =
        integer("distance_meters")
    val averageSpeedKmh = double("average_speed_kmh")
    val maxSpeedKmh = double("max_speed_kmh")
    val calories = integer("calories")
    val isLegit = bool("is_legit")

    override val primaryKey = PrimaryKey(id)
}