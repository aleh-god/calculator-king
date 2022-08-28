package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import androidx.annotation.StringRes

sealed interface GameAddFormUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : GameAddFormUiEvent

    object NavigateToBackScreen : GameAddFormUiEvent
    data class NavigateToPartyCard(val navArg: Long) : GameAddFormUiEvent
}
