package by.godevelopment.kingcalculator.domain.partiesdomain.models

import by.godevelopment.kingcalculator.commons.INT_ZERO_STUB
import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class PartyInfoItemModel(
    val id: Int = INT_ZERO_STUB,
    val gameType: GameType,
    val oneGameScore: Int? = null,
    val twoGameScore: Int? = null,
    val threeGameScore: Int? = null,
    val fourGameScore: Int? = null
)
