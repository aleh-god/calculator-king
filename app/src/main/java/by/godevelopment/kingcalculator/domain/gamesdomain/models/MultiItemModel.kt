package by.godevelopment.kingcalculator.domain.gamesdomain.models

import androidx.annotation.ColorRes
import by.godevelopment.kingcalculator.commons.INT_ZERO_STUB
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.Players
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

data class MultiItemModel(
    val rowId: Int,
    val itemViewType: Int,
    val player: PlayerModel,
    val playerNumber: Players,
    @ColorRes
    val colorPlayer: Int = android.R.color.white,
    val gameType: GameType,
    val tricks: Int = INT_ZERO_STUB,
    val score: Int = INT_ZERO_STUB,
    val hasError: Boolean = false,
    val totalPlayerScore: Int = INT_ZERO_STUB
)
