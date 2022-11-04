package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

import by.godevelopment.kingcalculator.commons.EMPTY_STRING

data class AddPartyFormState(
    val partyName: String = EMPTY_STRING,
    val partyNameError: Int? = null,

    val playerOneName: String = EMPTY_STRING,
    val playerOneError: Int? = null,

    val playerTwoName: String = EMPTY_STRING,
    val playerTwoError: Int? = null,

    val playerThreeName: String = EMPTY_STRING,
    val playerThreeError: Int? = null,

    val playerFourName: String = EMPTY_STRING,
    val playerFourError: Int? = null,

    val players: Map<String, Long> = emptyMap(),
    val showsProgress: Boolean = false
)
