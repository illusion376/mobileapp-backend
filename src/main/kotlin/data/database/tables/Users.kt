package data.database.tables
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

object Users : Table() {
    val id = uuid("id")
    val email = varchar("email", 255).uniqueIndex()
    val login = varchar("login", 255)
    val passwordHash = varchar("password", 255)
    val username = varchar("username", 255)
    val streak = integer("streak").default(0)
    val difficulty = double("difficulty").default(1.0)
    val lastActiveDate = timestamp("last_active_date").nullable()
    val createdAccountTime = timestamp("created_account_time").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}
