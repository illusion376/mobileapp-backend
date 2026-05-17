package data.database.tables
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

object WorkoutSessions : Table("workout_sessions") {
    val id = uuid("id")
    val userId = uuid("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val startTime = timestamp("start_time").defaultExpression(CurrentTimestamp)
    val endTime = timestamp("end_time").nullable()
    val distanceMeters = integer("distance_meters").default(0)
    val stepsDone = integer("steps_done").default(0)
    val avgSpeed = double("avg_speed").default(0.0)
    val maxSpeed = double("max_speed").default(0.0)
    val status = varchar("status", 20).default("IN_PROGRESS")
    val isValid = boolean("is_valid").default(true)

    override val primaryKey = PrimaryKey(id)
}