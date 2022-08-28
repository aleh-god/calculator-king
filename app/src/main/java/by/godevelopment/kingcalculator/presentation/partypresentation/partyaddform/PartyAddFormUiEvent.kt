package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

import androidx.annotation.StringRes

sealed interface PartyAddFormUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : PartyAddFormUiEvent

    object NavigateToBackScreen : PartyAddFormUiEvent
    data class NavigateToList(val idParty: Long) : PartyAddFormUiEvent
}