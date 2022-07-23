package by.godevelopment.kingcalculator.domain.gamesdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.FOOTER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.HEADER_ROW_TYPE
import by.godevelopment.kingcalculator.data.entities.PlayerProfile
import by.godevelopment.kingcalculator.domain.commons.models.GameType

sealed class MultiItemModel {
    abstract val rowId: Int
    abstract val itemViewType: Int
}

data class HeaderItemModel(
    override val rowId: Int,
    val player: PlayerProfile,
    @StringRes
    val playerNumberRes: Int,
    @ColorRes
    val colorPlayer: Int = android.R.color.white
) : MultiItemModel() {
    override val itemViewType: Int = HEADER_ROW_TYPE
}

data class BodyItemModel(
    override val rowId: Int,
    @StringRes
    val gameType: GameType,
    val tricks: Int = 0,
    val score: Int = 0,
    val hasError: Boolean = false
) : MultiItemModel() {
    override val itemViewType: Int = BODY_ROW_TYPE
}

data class FooterItemModel(
    override val rowId: Int,
    val totalPlayerScore: Int = 0
) : MultiItemModel() {
    override val itemViewType: Int = FOOTER_ROW_TYPE
}
