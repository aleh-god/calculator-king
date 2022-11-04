package by.godevelopment.kingcalculator.domain.gamesdomain.models

import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB
import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class TricksNoteModel(
    val id: Long = LONG_ZERO_STUB,
    val gameId: Long,
    val playerId: Long,
    val gameType: GameType,
    val tricksCount: Int
)
