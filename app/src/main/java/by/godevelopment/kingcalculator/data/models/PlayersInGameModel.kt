package by.godevelopment.kingcalculator.data.models

import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players

data class PlayersInGameModel(
    val gameId: Long = -1,
    val playersMap: HashMap<Players, PlayerProfile>
)
