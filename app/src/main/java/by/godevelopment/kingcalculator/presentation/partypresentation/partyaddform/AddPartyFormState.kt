package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

data class AddPartyFormState(
    val partyName: String = "",
    val partyNameError: Int? = null,

    val playerOneName: String = "",
    val playerOneError: Int? = null,

    val playerTwoName: String = "",
    val playerTwoError: Int? = null,

    val playerThreeName: String = "",
    val playerThreeError: Int? = null,

    val playerFourName: String = "",
    val playerFourError: Int? = null,

    val players: Map<String, Long> = emptyMap(),
    val showsProgress: Boolean = false
)
