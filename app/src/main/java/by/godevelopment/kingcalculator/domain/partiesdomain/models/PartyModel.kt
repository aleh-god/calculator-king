package by.godevelopment.kingcalculator.domain.partiesdomain.models

data class PartyModel(
    val id: Long = 0,
    val partyName: String,
    val startedAt: Long,
    val partyGamesCount: Int,
    val player_one_name: String,
    val player_one_score: Int,
    val player_two_name: String,
    val player_two_score: Int,
    val player_three_name: String,
    val player_three_score: Int,
    val player_four_name: String,
    val player_four_score: Int
)
