package by.godevelopment.kingcalculator.domain.gamesdomain.models

import by.godevelopment.kingcalculator.commons.EMPTY_STRING

data class MultiItemModel(
    val rowId: Int,
    val itemViewType: Int,
    val headerText: String = EMPTY_STRING,
    val footerText: String = EMPTY_STRING,
    val gameTypesName: String = EMPTY_STRING,
    val totalScore: Int = 0,
    val totalTricks: Int = 0,
    val hasError: Boolean = false
)