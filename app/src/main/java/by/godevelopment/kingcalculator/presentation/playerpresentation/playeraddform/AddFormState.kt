package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

import by.godevelopment.kingcalculator.commons.EMPTY_STRING

data class AddFormState(
    val playerName: String = EMPTY_STRING,
    val playerNameError: Int? = null,
    val realName: String = EMPTY_STRING,
    val realNameError: Int? = null,
    val showsProgress: Boolean = false
)
