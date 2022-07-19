package by.godevelopment.kingcalculator.domain.gamesdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.FOOTER_ROW_TYPE
import by.godevelopment.kingcalculator.commons.HEADER_ROW_TYPE
import by.godevelopment.kingcalculator.data.entities.PlayerProfile

sealed class MultiItemModel {
    abstract val rowId: Int
    abstract val itemViewType: Int
}

data class HeaderItemModel(
    override val rowId: Int,
    override val itemViewType: Int = HEADER_ROW_TYPE,
    val player: PlayerProfile,
    @StringRes
    val playerNumberRes: Int,
    @ColorRes
    val colorPlayer: Int = android.R.color.white
) : MultiItemModel()

data class BodyItemModel(
    override val rowId: Int,
    override val itemViewType: Int = BODY_ROW_TYPE,
    @StringRes
    val gameTypesName: Int,
    val totalTricks: Int = 0,
    val totalScore: Int = 0,
    val hasError: Boolean = false
) : MultiItemModel()

data class FooterItemModel(
    override val rowId: Int,
    override val itemViewType: Int = FOOTER_ROW_TYPE,
    val totalGameScore: Int = 0
) : MultiItemModel()
