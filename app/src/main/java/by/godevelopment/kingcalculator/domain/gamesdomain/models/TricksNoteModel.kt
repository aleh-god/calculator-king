package by.godevelopment.kingcalculator.domain.gamesdomain.models

data class TricksNoteModel(
    val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val gameType: String,
    val tricksCount: Int
)
