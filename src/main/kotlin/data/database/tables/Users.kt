package data.database.tables
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255)
    val passwordHash = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}