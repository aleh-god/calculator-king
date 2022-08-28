package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

sealed interface AddFormUserEvent {
    data class EmailChanged(val email: String) : AddFormUserEvent
    data class PlayerNameChanged(val playerName: String) : AddFormUserEvent
    object PressSaveButton : AddFormUserEvent
}
