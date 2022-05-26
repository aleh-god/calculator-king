package by.godevelopment.kingcalculator.presentation.partyaddform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.usecases.validationusecase.ValidatePlayerNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyAddFormViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val stringHelper: StringHelper,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddPartyFormState> = MutableStateFlow(AddPartyFormState())
    val uiState: StateFlow<AddPartyFormState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null
    private var partyId: Int? = null

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(players = playerRepository.getAllPlayersNames())
            }
        }
    }

    fun onEvent(event: AddPartyFormUserEvent) {
        when(event) {
            is AddPartyFormUserEvent.PartyNameChanged -> {
                val result = validatePlayerNameUseCase.execute(event.partyName)
                _uiState.update { it.copy(
                    partyName = event.partyName,
                    partyNameError = result.errorMessage
                ) }
            }
            is AddPartyFormUserEvent.PlayerOneNameChanged -> {
                _uiState.update { it.copy(
                    playerOneName = event.playerOneName,
                    playerOneHelper = "player@email.king"
                ) }
            }
            is AddPartyFormUserEvent.PlayerTwoNameChanged -> {
                _uiState.update { it.copy(
                    playerTwoName = event.playerTwoName,
                    playerTwoHelper = "player@email.king"
                ) }
            }
            is AddPartyFormUserEvent.PlayerThreeNameChanged -> {
                _uiState.update { it.copy(
                    playerThreeName = event.playerThreeName,
                    playerThreeHelper = "player@email.king"
                ) }
            }
            is AddPartyFormUserEvent.PlayerFourNameChanged -> {
                _uiState.update { it.copy(
                    playerFourName = event.playerFourName,
                    playerFourHelper = "player@email.king"
                ) }
            }
            is AddPartyFormUserEvent.PressStartButton -> {
                if(!checkErrorInFiledUiState()) {
                    savePartyDataToRepositoryAndReturnId()
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
        }
    }

    private fun checkErrorInFiledUiState(): Boolean {
        Log.i(TAG, "checkErrorInFiledUiState: invoke")
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowSnackbar("checkErrorInFiledUiState: invoke")
            )
        }
        return false
    }

    private fun savePartyDataToRepositoryAndReturnId(): Int {
        Log.i(TAG, "savePartyDataToRepository: invoke")
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.ShowSnackbar("savePartyDataToRepository: invoke")
            )
        }
        return -1
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavigateToList : UiEvent()
    }
}