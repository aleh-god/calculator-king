package by.godevelopment.kingcalculator.domain.playersdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class PlayerModel(
    val id: Long = 0,
    val name: String,
    val email: String,
    val isActive: Boolean,
    @DrawableRes
    val avatar: Int? = null,
    @ColorRes
    val color: Int? = null
)
