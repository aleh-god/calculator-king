package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.ValidateEmailUseCase
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerAddFormViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _uiState: MutableStateFlow<AddFormState> = MutableStateFlow(AddFormState())
    val uiState: StateFlow<AddFormState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    fun onEvent(event: AddFormUserEvent) {
        when(event) {
            is AddFormUserEvent.EmailChanged -> {
                val emailResult = validateEmailUseCase.execute(event.email)
                _uiState.update {
                    it.copy(
                        email = event.email,
                        emailError = emailResult.errorMessage
                    )
                }
            }
            is AddFormUserEvent.PlayerNameChanged -> {
                val playerNameResult = validatePlayerNameUseCase
                    .execute(event.playerName)
                _uiState.update {
                    it.copy(
                        playerName = event.playerName,
                        playerNameError = playerNameResult.errorMessage
                    )
                }
            }
            is AddFormUserEvent.PressSaveButton -> {
                if(!checkErrorInFiledUiState()) {
                    savePlayerDataToRepository()
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.ShowSnackbar(R.string.message_error_player_info))
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
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerRepository.createPlayer(
                PlayerModel(
                    name = uiState.value.playerName,
                    email = uiState.value.email
                )
            )
            when (result) {
                is ResultDataBase.Error -> { _uiEvent.send(UiEvent.ShowSnackbar(result.message)) }
                is ResultDataBase.Success -> { _uiEvent.send(UiEvent.NavigateToList) }
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: Int) : UiEvent()
        object NavigateToList : UiEvent()
    }
}
