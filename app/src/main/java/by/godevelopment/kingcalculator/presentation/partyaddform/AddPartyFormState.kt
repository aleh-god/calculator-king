package by.godevelopment.kingcalculator.presentation.partyaddform

data class AddPartyFormState(
    val partyName: String = "",
    val partyNameError: String? = null,

    val playerOneName: String = "",
    val playerOneError: String? = null,
    val playerOneHelper: String? = null,

    val playerTwoName: String = "",
    val playerTwoError: String? = null,
    val playerTwoHelper: String? = null,

    val playerThreeName: String = "",
    val playerThreeError: String? = null,
    val playerThreeHelper: String? = null,

    val playerFourName: String = "",
    val playerFourError: String? = null,
    val playerFourHelper: String? = null,

    val players: Map<String, String> = emptyMap(),
    val showsProgress: Boolean = false
)
