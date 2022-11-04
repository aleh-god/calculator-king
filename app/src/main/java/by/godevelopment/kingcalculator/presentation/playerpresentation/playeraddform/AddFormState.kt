package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

import by.godevelopment.kingcalculator.commons.EMPTY_STRING

data class AddFormState(
    val email: String = EMPTY_STRING,
    val emailError: Int? = null,
    val playerName: String = EMPTY_STRING,
    val playerNameError: Int? = null,
    val showsProgress: Boolean = false
)
