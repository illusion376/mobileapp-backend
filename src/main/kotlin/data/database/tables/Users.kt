package data.database.tables
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

object Users : Table() {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val login = varchar("login", 255)
    val passwordHash = varchar("password", 255)
    val lastActiveDate = timestamp("last_active_date").nullable()
    val createdAccountTime = timestamp("created_account_time").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
