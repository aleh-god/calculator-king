package by.godevelopment.kingcalculator.domain.playersdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import by.godevelopment.kingcalculator.commons.LONG_ZERO_STUB

data class PlayerModel(
    val id: Long = LONG_ZERO_STUB,
    val name: String,
    val email: String,
    val isActive: Boolean,
    @DrawableRes
    val avatar: Int? = null,
    @ColorRes
    val color: Int? = null
)
