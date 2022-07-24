package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

data class AddFormState(
    val email: String = "",
    val emailError: Int? = null,
    val playerName: String = "",
    val playerNameError: Int? = null,
    val showsProgress: Boolean = false
)
