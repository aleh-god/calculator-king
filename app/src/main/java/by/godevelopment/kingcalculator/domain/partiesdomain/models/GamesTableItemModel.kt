package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.commons.INT_ZERO_STUB
import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class GamesTableItemModel(
    val id: Int = INT_ZERO_STUB,
    val gameType: GameType,
    val isFinishedOneGame: Boolean = false,
    val isFinishedTwoGame: Boolean = false,
    val isFinishedThreeGame: Boolean = false,
    val isFinishedFourGame: Boolean = false,
    val openedColumnIndex: Int = INT_ZERO_STUB
)
