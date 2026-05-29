package quests

import data.database.tables.PlayerQuests
import data.database.tables.QuestTemplates
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import quests.models.*

class QuestRepository {

    fun getPlayerQuests(
        playerId: Int
    ): List<Pair<PlayerQuest, QuestTemplate>> {

        return transaction {

            (PlayerQuests innerJoin QuestTemplates)
                .select {
                    PlayerQuests.playerId eq playerId
                }
                .map {

                    val playerQuest =
                        PlayerQuest(
                            id = it[PlayerQuests.id],
                            playerId =
                                it[PlayerQuests.playerId],
                            questId =
                                it[PlayerQuests.questId],
                            progress =
                                it[PlayerQuests.progress],
                            completed =
                                it[PlayerQuests.completed],
                            claimed =
                                it[PlayerQuests.claimed]
                        )

                    val template =
                        QuestTemplate(
                            id = it[QuestTemplates.id],
                            title =
                                it[QuestTemplates.title],
                            description =
                                it[QuestTemplates.description],
                            category =
                                it[QuestTemplates.category],
                            objectiveType =
                                it[QuestTemplates.objectiveType],
                            targetValue =
                                it[QuestTemplates.targetValue],
                            rewardXp =
                                it[QuestTemplates.rewardXp],
                            isActive =
                                it[QuestTemplates.isActive]
                        )

                    playerQuest to template
                }
        }
    }

    fun completeQuest(
        playerQuestId: Int
    ) {

        transaction {

            PlayerQuests.update({
                PlayerQuests.id eq playerQuestId
            }) {

                it[completed] = true
            }
        }
    }

    fun claimQuest(
        playerQuestId: Int
    ) {

        transaction {

            PlayerQuests.update({
                PlayerQuests.id eq playerQuestId
            }) {

                it[claimed] = true
            }
        }
    }

    fun updateProgress(
        playerQuestId: Int,
        progress: Int
    ) {

        transaction {

            PlayerQuests.update({
                PlayerQuests.id eq playerQuestId
            }) {

                it[PlayerQuests.progress] = progress
            }
        }
    }
}