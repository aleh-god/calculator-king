package by.godevelopment.kingcalculator.presentation.partypresentation.partieslist

import androidx.annotation.StringRes

sealed interface PartiesListUiEvent {

    data class ShowMessage(
        @StringRes
        val message: Int,
        @StringRes
        val textAction: Int,
        val onAction: () -> Unit
    ) : PartiesListUiEvent

    object NavigateToPartyAddForm : PartiesListUiEvent
    data class NavigateToPartyCard(val navArgs: Long) : PartiesListUiEvent
    data class NavigateToPartyInfo(val navArgs: Long) : PartiesListUiEvent
}