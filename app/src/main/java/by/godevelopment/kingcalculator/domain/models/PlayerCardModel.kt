package by.godevelopment.kingcalculator.domain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class PlayerCardModel(
    val id: Int = 0,
    val name: String,
    val email: String,
    @ColorRes
    val avatar: Int? = null,
    @DrawableRes
    val color: Int? = null
)