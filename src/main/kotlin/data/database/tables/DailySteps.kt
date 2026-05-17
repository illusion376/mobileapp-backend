package data.database.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

object DailySteps : Table("daily_steps") {
    val id = uuid("id")
    val userId = uuid("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
    val date = date("date")
    val stepsCount = integer("steps_count").default(0)


    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(userId, date)
    }