package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.GetActivePlayerByIdUseCase
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
    private val playerRepository: PlayerRepository, // TODO("Split interface")
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<CardUiState> = MutableStateFlow(
        CardUiState(
            PlayerModel(
                name = "",
                email = "",
                isActive = false
            )
        )
    )
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private val idPlayer = state.get<Long>("idPlayer")
    private var suspendJob: Job? = null

    init {
        Log.i(TAG, "PlayerCardViewModel: $idPlayer")
        loadPlayerCardModelById(idPlayer)
    }

    private fun loadPlayerCardModelById(idPlayer: Long?) {
        viewModelScope.launch(ioDispatcher) {
            if (idPlayer != null) {
                _uiState.update { it.copy(showsProgress = true) }
                val playerResult = getActivePlayerByIdUseCase(idPlayer)
                when(playerResult) {
                    is ResultDataBase.Error -> _uiEvent.send(
                        UiEvent.ShowSnackbar(playerResult.message)
                    )
                    is ResultDataBase.Success -> _uiState.update {
                        it.copy(playerModel = playerResult.value)
                    }
                }
                _uiState.update { it.copy(showsProgress = false) }
            } else {
                _uiEvent.send(UiEvent.ShowSnackbar(R.string.message_error_player_id))
            }
        }
    }

    fun onEvent(event: CardUserEvent) {
        when(event) {
            is CardUserEvent.PlayerNameChanged -> {
                val playerNameResult = validatePlayerNameUseCase
                    .execute(event.playerName)
                _uiState.update { state ->
                    state.copy(
                        playerModel = state.playerModel.copy(name = event.playerName),
                        playerNameError = playerNameResult.errorMessage
                    )
                }
            }
            is CardUserEvent.PressSaveButton -> {
                val playerNameResult =
                    validatePlayerNameUseCase.execute(_uiState.value.playerModel.name)
                if(playerNameResult.successful) {
                    updatePlayerDataToRepository()
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.ShowSnackbar(R.string.message_error_player_info))
                    }
                }
            }
            is CardUserEvent.PressDeleteButton -> {
                deletePlayerDataFromRepository()
            }
        }
    }

    private fun deletePlayerDataFromRepository() {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerRepository.disablePlayerById(uiState.value.playerModel)
            when (result) {
                is ResultDataBase.Error -> _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                is ResultDataBase.Success -> _uiEvent.send(UiEvent.NavigateToList)
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    private fun updatePlayerDataToRepository() {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerRepository.updatePlayerById(uiState.value.playerModel)
            when (result) {
                is ResultDataBase.Error -> _uiEvent.send(UiEvent.ShowSnackbar(result.message))
                is ResultDataBase.Success -> _uiEvent.send(UiEvent.NavigateToList)
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: Int) : UiEvent()
        object NavigateToList : UiEvent()
    }
}
