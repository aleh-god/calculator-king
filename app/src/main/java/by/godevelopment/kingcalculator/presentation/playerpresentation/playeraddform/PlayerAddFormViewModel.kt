package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerAddFormViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddFormState> = MutableStateFlow(AddFormState())
    val uiState: StateFlow<AddFormState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PlayerAddFormUiEvent>()
    val uiEvent: Flow<PlayerAddFormUiEvent> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    fun onEvent(event: AddFormUserEvent) {
        when (event) {
            is AddFormUserEvent.RealNameChanged -> {
                _uiState.update {
                    it.copy(
                        realName = event.realName,
                        realNameError = validatePlayerNameUseCase(event.realName).errorMessage
                    )
                }
            }
            is AddFormUserEvent.PlayerNameChanged -> {
                _uiState.update {
                    it.copy(
                        playerName = event.playerName,
                        playerNameError = validatePlayerNameUseCase(event.playerName).errorMessage
                    )
                }
            }
            is AddFormUserEvent.PressSaveButton -> {
                if (!checkErrorInFiledUiState()) {
                    savePlayerDataToRepository()
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(
                            PlayerAddFormUiEvent.ShowMessage(
                                message = R.string.message_error_player_info,
                                textAction = R.string.snackbar_btn_neutral_ok,
                                onAction = { }
                            )
                        )
                    }
                }
            }
        }
    }

    private fun checkErrorInFiledUiState(): Boolean {
        return listOf(
            validatePlayerNameUseCase(_uiState.value.realName),
            validatePlayerNameUseCase(_uiState.value.playerName)
        ).any { !it.successful }
    }

    private fun savePlayerDataToRepository() {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerRepository.createPlayer(
                PlayerModel(
                    name = uiState.value.playerName,
                    realName = uiState.value.realName,
                    isActive = true
                )
            )
            when (result) {
                is ResultDataBase.Error -> {
                    _uiEvent.send(
                        PlayerAddFormUiEvent.ShowMessage(
                            message = result.message,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = { }
                        )
                    )
                }
                is ResultDataBase.Success -> { _uiEvent.send(PlayerAddFormUiEvent.NavigateToBackScreen) }
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }
}
