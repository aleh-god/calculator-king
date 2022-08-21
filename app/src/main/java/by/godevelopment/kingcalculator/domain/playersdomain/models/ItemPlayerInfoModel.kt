package by.godevelopment.kingcalculator.domain.playersdomain.models

import androidx.annotation.StringRes

data class ItemPlayerInfoModel(
    @StringRes
    val type: Int,
    val value: String
)
