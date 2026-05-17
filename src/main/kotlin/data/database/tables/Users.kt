package data.database.tables
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255)
    val login = varchar("login", 255)
    val passwordHash = varchar("password", 255)
    val isVerified = bool("status")

    override val primaryKey = PrimaryKey(id)
}