package by.godevelopment.kingcalculator.presentation.playercard

sealed class CardUserEvent {
    data class PlayerNameChanged(val playerName: String) : CardUserEvent()
    object PressSaveButton: CardUserEvent()
    object PressDeleteButton: CardUserEvent()
}