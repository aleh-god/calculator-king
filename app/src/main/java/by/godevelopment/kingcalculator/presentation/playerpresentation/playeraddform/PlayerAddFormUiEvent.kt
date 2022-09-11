package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

import androidx.annotation.StringRes

sealed interface PlayerAddFormUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : PlayerAddFormUiEvent

    object NavigateToBackScreen : PlayerAddFormUiEvent
}
