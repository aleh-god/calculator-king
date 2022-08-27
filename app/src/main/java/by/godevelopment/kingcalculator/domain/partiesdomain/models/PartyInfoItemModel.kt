package by.godevelopment.kingcalculator.domain.partiesdomain.models

import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.domain.commons.models.GameType

data class PartyInfoItemModel(
    val id: Int = 0,
    @StringRes
    val gameType: GameType,
    val oneGameScore: Int? = null,
    val twoGameScore: Int? = null,
    val threeGameScore: Int? = null,
    val fourGameScore: Int? = null
)