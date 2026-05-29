package quests.models

data class QuestTemplate(
    val id: Int,
    val title: String,
    val description: String,
    val category: QuestCategory,
    val objectiveType: QuestObjectiveType,
    val targetValue: Int,
    val rewardXp: Int,
    val isActive: Boolean
)