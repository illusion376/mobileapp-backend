package data.database.tables

import data.database.tables.Users.integer
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ServerPlayers : Table("server_players") {
    val id = integer("id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val level = integer("level")
    val experience = integer("experience")
    val strength = integer("strength")
    val stamina = integer("stamina")
    val vitality = integer("vitality")
    val steps = integer("steps")
    override val primaryKey = PrimaryKey(id)
}