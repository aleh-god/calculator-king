package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

sealed interface CardUserEvent {
    data class PlayerNameChanged(val playerName: String) : CardUserEvent
    object PressSaveButton : CardUserEvent
    object PressDeleteButton : CardUserEvent
}
