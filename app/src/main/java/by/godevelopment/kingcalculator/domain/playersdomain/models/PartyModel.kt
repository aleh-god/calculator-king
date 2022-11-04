package by.godevelopment.kingcalculator.domain.playersdomain.models

import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB

data class PartyModel(
    val id: Long = LONG_ZERO_STUB,
    val partyName: String,
    val startedAt: Long,
    val partyLastTime: Long,
    val playerOneId: Long,
    val playerTwoId: Long,
    val playerThreeId: Long,
    val playerFourId: Long
)
