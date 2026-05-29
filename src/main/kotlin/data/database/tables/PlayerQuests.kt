package data.database.tables

import org.jetbrains.exposed.sql.Table

object PlayerQuests : Table("player_quests") {
    val id = integer("id").autoIncrement()
    val playerId = integer("player_id")
        .references(Users.id)
    val questId = integer("quest_id")
        .references(QuestTemplates.id)
    val progress = integer("progress")
    val completed = bool("completed")
    val claimed = bool("claimed")

    override val primaryKey = PrimaryKey(id)
}