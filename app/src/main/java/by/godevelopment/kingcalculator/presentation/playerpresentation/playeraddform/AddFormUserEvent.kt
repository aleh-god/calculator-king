package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

sealed interface AddFormUserEvent {
    data class RealNameChanged(val realName: String) : AddFormUserEvent
    data class PlayerNameChanged(val playerName: String) : AddFormUserEvent
    object PressSaveButton : AddFormUserEvent
}
