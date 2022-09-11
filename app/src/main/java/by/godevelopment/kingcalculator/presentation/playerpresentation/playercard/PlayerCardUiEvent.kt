package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

import androidx.annotation.StringRes

sealed interface PlayerCardUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : PlayerCardUiEvent

    object NavigateToBackScreen : PlayerCardUiEvent
}
