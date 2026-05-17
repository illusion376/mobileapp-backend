package data.database.tables
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption

object QuestsList : Table("quest_list") {
    val id = integer("id").autoIncrement()
    val type = varchar("type", 50)
    val metric = varchar("metric", 50)
    val baseTarget = integer("base_target")
    val baseXp = integer("base_xp")
    val title = varchar("title", 100)
    val description = text("description").nullable()

    override val primaryKey = PrimaryKey(id)
}