package by.godevelopment.kingcalculator.presentation.settingsscreen

import androidx.annotation.StringRes

sealed interface SettingsUiEvent {

    data class ShowMessage(
        @StringRes val message: Int,
        @StringRes val textAction: Int,
        val valueForText: Int? = null,
        val onAction: () -> Unit
    ) : SettingsUiEvent

    object NavigateToSystemSettings : SettingsUiEvent
}
