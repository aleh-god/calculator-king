package by.godevelopment.kingcalculator.domain.gamesdomain.models

import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class TricksNoteModel(
    val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val gameType: GameType,
    val tricksCount: Int
)
