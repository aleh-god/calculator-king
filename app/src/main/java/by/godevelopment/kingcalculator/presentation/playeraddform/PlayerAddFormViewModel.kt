package by.godevelopment.kingcalculator.presentation.playeraddform

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
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
        val currentState = uiState.value
        if (currentState.nameIsValid && currentState.emailIsValid) {
            _uiState.value = currentState.copy(isSavingData = true)
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                val result = savePlayerDataToRepositoryUseCase.run(
                    PlayerCardModel(
                        name = name.toString(),
                        email = email.toString()
                    )
                )
                if (result) {
                    _uiEvent.emit(stringHelper.getString(R.string.message_data_load))
                } else {
                    _uiEvent.emit(stringHelper.getString(R.string.message_error_data_load))
                }
            }
            _uiState.value = uiState.value.copy(isSavingData = false)
        }
    }

    fun checkNameFields(name: Editable?) {
        val checkedName = !name.isNullOrBlank() && name.length > 2
        Log.i(TAG, "checkNameFields: $name = $checkedName")
        val currentState = uiState.value
         _uiState.value = currentState.copy(
             nameIsValid = checkedName
         )
    }

    fun checkEmailFields(email: Editable?) {
        val checkedEmail = !email.isNullOrBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        Log.i(TAG, "checkEmailFields: $email = $checkedEmail")
        val currentState = uiState.value
        _uiState.value = currentState.copy(
            emailIsValid = checkedEmail
        )
    }

    data class UiState(
        val isSavingData: Boolean = false,
        val nameIsValid: Boolean = false,
        val emailIsValid: Boolean = false
    )
}