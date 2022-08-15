package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import androidx.annotation.StringRes

sealed interface PartyCardUiEvent {

    data class ShowMessage(
        @StringRes
        val message: Int,
        val onAction: () -> Unit
    ) : PartyCardUiEvent

    data class NavigateToGameAddForm(val navArgs: Long) : PartyCardUiEvent
}