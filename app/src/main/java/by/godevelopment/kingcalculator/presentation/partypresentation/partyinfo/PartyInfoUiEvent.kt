package by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo

import androidx.annotation.StringRes

sealed interface PartyInfoUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : PartyInfoUiEvent

    object NavigateToBackScreen : PartyInfoUiEvent
}
