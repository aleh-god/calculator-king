package by.godevelopment.kingcalculator.presentation.playerpresentation.playerslist

import androidx.annotation.StringRes

sealed interface PlayersListUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : PlayersListUiEvent

    object NavigateToBackScreen : PlayersListUiEvent
}
