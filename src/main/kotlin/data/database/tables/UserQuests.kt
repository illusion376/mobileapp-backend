package data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

object UserQuests : Table("user_quests") {
    val id = uuid("id")
    val userId = uuid("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val templateId = integer("template_id").references(QuestsList.id, onDelete = ReferenceOption.RESTRICT)
    val targetValue = integer("target_value")
    val currentProgress = integer("current_progress").default(0)
    val xpReward = integer("xp_reward")
    val status = varchar("status", 20).default("ACTIVE")
    val assignedAt = timestamp("assigned_at").defaultExpression(CurrentTimestamp())
    val expiresAt = timestamp("expires_at")

    override val primaryKey = PrimaryKey(id)
}