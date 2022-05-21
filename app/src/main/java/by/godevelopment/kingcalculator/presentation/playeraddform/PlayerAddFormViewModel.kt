package by.godevelopment.kingcalculator.presentation.playeraddform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import by.godevelopment.kingcalculator.domain.usecases.SavePlayerDataToRepositoryUseCase
import by.godevelopment.kingcalculator.domain.usecases.validationusecase.ValidateEmailUseCase
import by.godevelopment.kingcalculator.domain.usecases.validationusecase.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerAddFormViewModel @Inject constructor(
    private val savePlayerDataToRepositoryUseCase: SavePlayerDataToRepositoryUseCase,
    private val stringHelper: StringHelper,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<AddFormState> = MutableStateFlow(AddFormState())
    val uiState: StateFlow<AddFormState> = _uiState

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    fun onEvent(event: AddFormUserEvent) {
        when(event) {
            is AddFormUserEvent.EmailChanged -> {
                val emailResult = validateEmailUseCase.execute(event.email)
                _uiState.value = _uiState.value.copy(
                    email = event.email,
                    emailError = emailResult.errorMessage
                )
            }
            is AddFormUserEvent.PlayerNameChanged -> {
                val playerNameResult = validatePlayerNameUseCase
                    .execute(event.playerName)
                _uiState.value = _uiState.value.copy(
                    playerName = event.playerName,
                    playerNameError = playerNameResult.errorMessage
                )
            }
            is AddFormUserEvent.PressSaveButton -> {
                if(!checkErrorInFiledUiState()) {
                    savePlayerDataToRepository()
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(stringHelper.getString(R.string.message_error_data_save))
                        )
                    }
                }
            }
        }
    }

    private fun checkErrorInFiledUiState(): Boolean {
        val emailResult = validateEmailUseCase.execute(_uiState.value.email)
        val playerNameResult = validatePlayerNameUseCase
            .execute(_uiState.value.playerName)
        return listOf(emailResult, playerNameResult).any { !it.successful }
    }

    private fun savePlayerDataToRepository() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.value = uiState.value.copy(showsProgress = true)
            if (savePlayerDataToRepositoryUseCase(
                    PlayerCardModel(
                        name = uiState.value.playerName,
                        email = uiState.value.email
                    )
                )
            ) {
                Log.i(TAG, "savePlayerDataToRepository: R.string.message_data_save")
                _uiEvent.send(UiEvent.NavigateToList)
            } else {
                Log.i(TAG, "savePlayerDataToRepository: R.string.message_error_data_save")
                _uiEvent.send(
                    UiEvent.ShowSnackbar(stringHelper.getString(R.string.message_error_data_save))
                )
            }
            _uiState.value = uiState.value.copy(showsProgress = false)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateToList : UiEvent()
    }
}
