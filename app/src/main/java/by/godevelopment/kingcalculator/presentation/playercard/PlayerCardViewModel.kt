package by.godevelopment.kingcalculator.presentation.playercard

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.PlayerCardModel
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.usecases.validationusecase.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerCardViewModel @Inject constructor(
    state: SavedStateHandle,
    private val playerRepository: PlayerRepository,
    private val stringHelper: StringHelper,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CardUiState> = MutableStateFlow(CardUiState(
        PlayerCardModel(
         name = "",
         email = ""
        )))
    val uiState: StateFlow<CardUiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private val idPlayer = state.get<Int>("idPlayer")
    private var suspendJob: Job? = null

    init {
        Log.i(TAG, "PlayerCardViewModel: $idPlayer")
        loadPlayerCardModelById(idPlayer)
    }

    private fun loadPlayerCardModelById(idPlayer: Int?) {
        viewModelScope.launch {
            if (idPlayer != null) {
                _uiState.update { it.copy(showsProgress = true) }
                val response = playerRepository.getPlayerById(idPlayer)
                if (response != null) {
                    _uiState.update { it.copy(playerCardModel = response) }
                } else {
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            stringHelper.getString(R.string.message_error_player_id)
                        )
                    )
                }
                _uiState.update { it.copy(showsProgress = false) }
            } else {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        stringHelper.getString(R.string.message_error_player_id)
                    )
                )
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
                        playerCardModel = state.playerCardModel.copy(name = event.playerName),
                        playerNameError = playerNameResult.errorMessage
                    )
                }
            }
            is CardUserEvent.PressSaveButton -> {
                val playerNameResult =
                    validatePlayerNameUseCase.execute(_uiState.value.playerCardModel.name)
                if(playerNameResult.successful) {
                    updatePlayerDataToRepository()
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(
                            UiEvent.ShowSnackbar(
                                stringHelper.getString(R.string.message_error_player_info)
                            )
                        )
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
        suspendJob = viewModelScope.launch {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerRepository.deletePlayerById(uiState.value.playerCardModel)
            if (result) {
                _uiEvent.send(UiEvent.NavigateToList)
            } else {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(stringHelper.getString(R.string.message_error_data_delete))
                )
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    private fun updatePlayerDataToRepository() {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch {
            _uiState.update { it.copy(showsProgress = true) }
            val result = playerRepository.updatePlayerById(uiState.value.playerCardModel)
            if (result) {
                _uiEvent.send(UiEvent.NavigateToList)
            } else {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(stringHelper.getString(R.string.message_error_data_save))
                )
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateToList : UiEvent()
    }
}