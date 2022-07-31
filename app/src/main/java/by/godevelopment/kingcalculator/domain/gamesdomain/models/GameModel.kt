package by.godevelopment.kingcalculator.domain.gamesdomain.models

import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class GameModel(
    val id: Long,
    val partyId: Long,
    val gameType: GameType,
    val finishedAt: Long
)