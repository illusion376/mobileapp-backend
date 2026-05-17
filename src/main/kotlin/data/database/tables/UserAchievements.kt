package data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

object UserAchievements : Table("user_achievements") {
    val userId = uuid("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val achievementId = integer("achievement_id").references(Achievements.id, onDelete = ReferenceOption.CASCADE)
    val unlockedAt = timestamp("unlocked_at").defaultExpression(CurrentTimestamp)

    override val primaryKey = PrimaryKey(userId, achievementId)
}