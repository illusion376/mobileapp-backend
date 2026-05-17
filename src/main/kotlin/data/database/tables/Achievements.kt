package data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object Achievements : Table("achievements") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 100)
    val description = text("description")
    val metric = varchar("metric", 50)
    val targetValue = integer("target_value")
    val xpReward = integer("xp_reward")

    override val primaryKey = PrimaryKey(id)
}