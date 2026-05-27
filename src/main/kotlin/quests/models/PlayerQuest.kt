package quests.models


data class PlayerQuest(
    val id: Int,
    val playerId: Int,
    val questId: Int,
    val progress: Int,
    val completed: Boolean,
    val claimed: Boolean
)