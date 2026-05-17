package data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import data.database.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://185.244.51.59:5432/mobileapp"
            driverClassName = "org.postgresql.Driver"
            username = "postgres"
            password = "asdf2606"
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }

        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }
}

fun initDatabase() {
    DatabaseFactory.init()

    transaction {
        SchemaUtils.create(
            Users,
            QuestsList,
            WorkoutSessions,
            DailySteps,
            UserQuests,
            Achievements,
            UserAchievements
            )
    }
}