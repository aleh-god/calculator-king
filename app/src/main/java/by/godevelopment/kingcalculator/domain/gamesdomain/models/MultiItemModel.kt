package by.godevelopment.kingcalculator.domain.gamesdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel

data class MultiItemModel(
    val rowId: Int,
    val itemViewType: Int,
    val player: PlayerModel,
    @StringRes
    val playerNumber: Players,
    @ColorRes
    val colorPlayer: Int = android.R.color.white,
    @StringRes
    val gameType: GameType,
    val tricks: Int = 0,
    val score: Int = 0,
    val hasError: Boolean = false,
    val totalPlayerScore: Int = 0
)
