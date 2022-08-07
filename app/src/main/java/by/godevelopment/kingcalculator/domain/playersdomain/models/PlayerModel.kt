package by.godevelopment.kingcalculator.domain.playersdomain.models

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

// TODO("Delete duplicate")
data class PlayerModel(
    val id: Long = 0,
    val name: String,
    val email: String,
    @ColorRes
    val avatar: Int? = null,
    @DrawableRes
    val color: Int? = null
)