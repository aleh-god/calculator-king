package by.godevelopment.kingcalculator.domain.partiesdomain.models

data class GamesTableItemModel (
    val id: Long = 0,
    val gameTypeName: String = "",
    val isFinishedOneGame: Boolean = false,
    val isFinishedTwoGame: Boolean = false,
    val isFinishedThreeGame: Boolean = false,
    val isFinishedFourGame: Boolean = false,
    val openedColumnNumber: Int = 0
)