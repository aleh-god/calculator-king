package by.godevelopment.kingcalculator.presentation.playeraddform

data class AddFormState(
    val email: String = "",
    val emailError: String? = null,
    val playerName: String = "",
    val playerNameError: String? = null,
    val showsProgress: Boolean = false
)
