package by.godevelopment.kingcalculator.presentation.playeraddform

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.PlayerDataModel
import by.godevelopment.kingcalculator.domain.usecases.SavePlayerDataToRepositoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerAddFormViewModel @Inject constructor(
    private val savePlayerDataToRepositoryUseCase: SavePlayerDataToRepositoryUseCase,
    private val stringHelper: StringHelper
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent  = MutableSharedFlow<String>(0)
    val uiEvent: SharedFlow<String> = _uiEvent

    private var fetchJob: Job? = null

    fun savePlayerDataToRepository(name: Editable?, email: Editable?) {
        _uiState.value = UiState(isFetchingData = true)
        if (checkTextFields(name, email)) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                val result = savePlayerDataToRepositoryUseCase.run(
                    PlayerDataModel(
                        name = name.toString(),
                        email = email.toString()
                    )
                )
                if (!result) {
                    _uiEvent.emit(stringHelper.getString(R.string.message_error_data_load))
                } else {
                    _uiEvent.emit(stringHelper.getString(R.string.message_data_load))
                }
            }
        }
        _uiState.value = UiState(isFetchingData = false)
    }

    private fun checkTextFields(name: Editable?, email: Editable?): Boolean {
        val checkedName = !name.isNullOrBlank()
        val checkedEmail = !email.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        viewModelScope.launch {
            if (!checkedName) _uiEvent.emit(stringHelper.getString(R.string.message_error_valid_name))
        }
        viewModelScope.launch {
            if (!checkedEmail) _uiEvent.emit(stringHelper.getString(R.string.message_error_valid_email))
        }
        Log.i(TAG, "checkTextFields: $checkedName + $checkedEmail = ${checkedName && checkedEmail}")
        return checkedName && checkedEmail
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val playerName: String? = null,
        val playerEmail: String? = null
    )
}