package by.godevelopment.kingcalculator.domain.partiesdomain.models

data class ItemPartyModel(
    val id: Long = 0,
    val partyName: String,
    val partyStartDate: String,
    val partyLastDate: String,
    val isComplete: Boolean,
    val partyGamesCount: String,
    val player_one_name: String,
    val player_one_score: String,
    val player_two_name: String,
    val player_two_score: String,
    val player_three_name: String,
    val player_three_score: String,
    val player_four_name: String,
    val player_four_score: String
)
