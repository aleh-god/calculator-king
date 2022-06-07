package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.data.entities.TrickNote

data class PartyModel(
    val id: Long = 0,
    val partyName: String,
    val startedAt: Long,
    val partyGamesCount: Int,
    val player_one_name: String,
    val player_one_tricks: List<TrickNote>,
    val player_two_name: String,
    val player_two_tricks: List<TrickNote>,
    val player_three_name: String,
    val player_three_tricks: List<TrickNote>,
    val player_four_name: String,
    val player_four_tricks: List<TrickNote>
)
