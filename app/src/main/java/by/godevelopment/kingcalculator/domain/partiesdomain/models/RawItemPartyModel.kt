package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.data.entities.TricksNote
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

data class RawItemPartyModel(
    val id: Long = 0,
    val partyName: String,
    val startedAt: Long,
    val partyLastTime: Long,
    val partyGamesCount: Int,
    val player_one: PlayerModel,
    val player_one_tricks: List<TricksNote>,
    val player_two: PlayerModel,
    val player_two_tricks: List<TricksNote>,
    val player_three: PlayerModel,
    val player_three_tricks: List<TricksNote>,
    val player_four: PlayerModel,
    val player_four_tricks: List<TricksNote>
)
