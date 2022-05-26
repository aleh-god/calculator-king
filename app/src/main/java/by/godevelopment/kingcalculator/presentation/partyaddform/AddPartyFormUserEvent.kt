package by.godevelopment.kingcalculator.presentation.partyaddform

sealed class AddPartyFormUserEvent {
    data class PartyNameChanged(val partyName: String) : AddPartyFormUserEvent()
    data class PlayerOneNameChanged(val playerOneName: String) : AddPartyFormUserEvent()
    data class PlayerTwoNameChanged(val playerTwoName: String) : AddPartyFormUserEvent()
    data class PlayerThreeNameChanged(val playerThreeName: String) : AddPartyFormUserEvent()
    data class PlayerFourNameChanged(val playerFourName: String) : AddPartyFormUserEvent()
    object PressStartButton : AddPartyFormUserEvent()
}