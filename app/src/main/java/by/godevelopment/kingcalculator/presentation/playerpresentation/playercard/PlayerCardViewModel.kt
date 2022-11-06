package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.EMPTY_STRING
import by.godevelopment.kingcalculator.commons.PLAYER_ID_NAVIGATION_ARGUMENT
import by.godevelopment.kingcalculator.commons.RELOAD_MAX_LIMIT
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerCardRepository
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.GetActivePlayerByIdUseCase
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerCardViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getActivePlayerByIdUseCase: GetActivePlayerByIdUseCase,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase,
    private val playerCardRepository: PlayerCardRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<CardUiState> = MutableStateFlow(
        CardUiState(
            PlayerModel(
                name = EMPTY_STRING,
                realName = EMPTY_STRING,
                isActive = false
            )
        )
    )
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PlayerCardUiEvent>()
    val uiEvent: Flow<PlayerCardUiEvent> = _uiEvent.receiveAsFlow()

    private val idPlayer = state.get<Long>(PLAYER_ID_NAVIGATION_ARGUMENT)
    private var fetchJob: Job? = null
    private var reloadsCount = 0

    init {
        fetchDataModel(idPlayer)
    }

    private fun reloadDataModel() {
        if (reloadsCount > RELOAD_MAX_LIMIT) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                reloadsCount = 0
                _uiEvent.send(PlayerCardUiEvent.NavigateToBackScreen)
            }
        } else {
            reloadsCount++
            fetchDataModel(idPlayer)
        }
    }

    private fun fetchDataModel(idPlayer: Long?) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (idPlayer != null) {
                _uiState.update { it.copy(showsProgress = true) }
                val playerResult = getActivePlayerByIdUseCase(idPlayer)
                when (playerResult) {
                    is ResultDataBase.Error -> _uiEvent.send(
                        PlayerCardUiEvent.ShowMessage(
                            message = playerResult.message,
                            textAction = R.string.snackbar_btn_reload,
                            onAction = ::reloadDataModel
                        )
                    )
                    is ResultDataBase.Success -> _uiState.update {
                        it.copy(playerModel = playerResult.value)
                    }
                }
                _uiState.update { it.copy(showsProgress = false) }
            } else {
                _uiEvent.send(
                    PlayerCardUiEvent.ShowMessage(
                        message = R.string.message_error_player_id,
                        textAction = R.string.snackbar_btn_reload,
                        onAction = ::reloadDataModel
                    )
                )
            }
        }
    }

    fun onEvent(event: CardUserEvent) {
        when (event) {
            is CardUserEvent.PlayerNameChanged -> {
                val playerNameResult = validatePlayerNameUseCase(event.playerName)
                _uiState.update { state ->
                    state.copy(
                        playerModel = state.playerModel.copy(name = event.playerName),
                        playerNameError = playerNameResult.errorMessage
                    )
                }
            }
            is CardUserEvent.PressSaveButton -> {
                _uiState.value.playerModel.name.let {
                    val playerNameResult =
                        validatePlayerNameUseCase(it)
                    if (it.isNotEmpty() && playerNameResult.successful) {
                        updatePlayerDataToRepository()
                    } else {
                        viewModelScope.launch {
                            _uiEvent.send(
                                PlayerCardUiEvent.ShowMessage(
                                    message = R.string.message_error_player_info,
                                    textAction = R.string.snackbar_btn_reload,
                                    onAction = ::reloadDataModel
                                )
                            )
                        }
                    }
                }
            }
            is CardUserEvent.PressDeleteButton -> {
                deletePlayerDataFromRepository()
            }
        }
    }

    private fun deletePlayerDataFromRepository() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerCardRepository.disablePlayerById(uiState.value.playerModel)
            when (result) {
                is ResultDataBase.Error -> _uiEvent.send(
                    PlayerCardUiEvent.ShowMessage(
                        message = result.message,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = { }
                    )
                )
                is ResultDataBase.Success -> _uiEvent.send(PlayerCardUiEvent.NavigateToBackScreen)
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    private fun updatePlayerDataToRepository() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerCardRepository.updatePlayerById(uiState.value.playerModel)
            when (result) {
                is ResultDataBase.Error -> _uiEvent.send(
                    PlayerCardUiEvent.ShowMessage(
                        message = result.message,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = { }
                    )
                )
                is ResultDataBase.Success -> _uiEvent.send(PlayerCardUiEvent.NavigateToBackScreen)
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }
}
