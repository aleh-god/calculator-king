package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import androidx.annotation.StringRes

sealed interface PlayerInfoUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val onAction: () -> Unit
    ) : PlayerInfoUiEvent

    object NavigateToBackScreen : PlayerInfoUiEvent
}
