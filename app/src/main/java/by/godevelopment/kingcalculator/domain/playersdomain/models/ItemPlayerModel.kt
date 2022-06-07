package by.godevelopment.kingcalculator.domain.playersdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class ItemPlayerModel(
    val id: Long = 0,
    val name: String,
    val email: String,
    @ColorRes
    val avatar: Int? = null,
    @DrawableRes
    val color: Int? = null
)
