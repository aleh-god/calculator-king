package by.godevelopment.kingcalculator.domain.playersdomain.models

data class PartyModel(
    val id: Long = 0,
    val partyName: String,
    val startedAt: Long,
    val partyLastTime: Long,
    val playerOneId: Long,
    val playerTwoId: Long,
    val playerThreeId: Long,
    val playerFourId: Long
)
