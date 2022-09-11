package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class GamesTableItemModel(
    val id: Int = 0,
    val gameType: GameType,
    val isFinishedOneGame: Boolean = false,
    val isFinishedTwoGame: Boolean = false,
    val isFinishedThreeGame: Boolean = false,
    val isFinishedFourGame: Boolean = false,
    val openedColumnIndex: Int = 0
)
