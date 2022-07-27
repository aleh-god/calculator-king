package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import androidx.annotation.StringRes

sealed interface GameAddFormUiEvent

data class ShowMessageUiEvent(
    @StringRes
    val message: Int,
    val onAction: () -> Unit
) :GameAddFormUiEvent

object NavigateToPartyCardUiEvent : GameAddFormUiEvent
