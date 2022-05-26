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

    val players: List<String> = listOf(), // listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val showsProgress: Boolean = false
)
