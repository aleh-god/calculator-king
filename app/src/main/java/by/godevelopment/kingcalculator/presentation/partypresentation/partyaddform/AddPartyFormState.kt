package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

data class AddPartyFormState(
    val partyName: String = "",
    val partyNameError: String? = null,

    val playerOneName: String = "",
    val playerOneError: String? = null,

    val playerTwoName: String = "",
    val playerTwoError: String? = null,

    val playerThreeName: String = "",
    val playerThreeError: String? = null,

    val playerFourName: String = "",
    val playerFourError: String? = null,

    val players: Map<String, Long> = emptyMap(),
    val showsProgress: Boolean = false
)
