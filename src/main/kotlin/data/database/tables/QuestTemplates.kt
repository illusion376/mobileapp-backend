package data.database.tables

import org.jetbrains.exposed.sql.Table
import quests.models.QuestCategory
import quests.models.QuestObjectiveType

object QuestTemplates : Table("quest_templates") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 255)
    val description = text("description")
    val category =
        enumerationByName<QuestCategory>(
            "category",
            32
        )
    val objectiveType =
        enumerationByName<QuestObjectiveType>(
            "objective_type",
            32
        )
    val targetValue = integer("target_value")
    val rewardXp = integer("reward_xp")
    val isActive = bool("is_active")

    override val primaryKey = PrimaryKey(id)
}