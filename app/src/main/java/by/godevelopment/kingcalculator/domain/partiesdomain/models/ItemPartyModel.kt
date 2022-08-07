package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

data class ItemPartyModel(
    val id: Long = 0,
    val partyName: String,
    val partyStartDate: String,
    val partyLastDate: String,
    val isComplete: Boolean,
    val player_one: PlayerModel,
    val player_two: PlayerModel,
    val player_three: PlayerModel,
    val player_four: PlayerModel,
    val partyGamesCount: String,
    val player_one_score: String,
    val player_two_score: String,
    val player_three_score: String,
    val player_four_score: String
)
