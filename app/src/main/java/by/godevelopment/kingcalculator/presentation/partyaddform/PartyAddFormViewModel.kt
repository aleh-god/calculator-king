package by.godevelopment.kingcalculator.presentation.partyaddform

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.BLANK_STRING_VALUE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.repositories.PartyRepository
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
    private val partyRepository: PartyRepository,
    private val stringHelper: StringHelper,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddPartyFormState> = MutableStateFlow(AddPartyFormState())
    val uiState: StateFlow<AddPartyFormState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(players = playerRepository.getAllPlayersEmailToNames())
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
                    playerOneHelper = getNameByEmail(event.playerOneName)
                ) }
            }
            is AddPartyFormUserEvent.PlayerTwoNameChanged -> {
                _uiState.update { it.copy(
                    playerTwoName = event.playerTwoName,
                    playerTwoHelper = getNameByEmail(event.playerTwoName)
                ) }
            }
            is AddPartyFormUserEvent.PlayerThreeNameChanged -> {
                _uiState.update { it.copy(
                    playerThreeName = event.playerThreeName,
                    playerThreeHelper = getNameByEmail(event.playerThreeName)
                ) }
            }
            is AddPartyFormUserEvent.PlayerFourNameChanged -> {
                _uiState.update { it.copy(
                    playerFourName = event.playerFourName,
                    playerFourHelper = getNameByEmail(event.playerFourName)
                ) }
            }
            is AddPartyFormUserEvent.PressStartButton -> {
                suspendJob?.cancel()
                suspendJob = viewModelScope.launch {
                    if(checkErrorInFiledUiState()) {
                        createNewPartyAndReturnId().let {
                            if (it < 0) {
                                _uiEvent.send(
                                    UiEvent.ShowSnackbar(
                                        stringHelper.getString(R.string.message_error_data_save)
                                    )
                                )
                            } else {
                                _uiEvent.send(UiEvent.NavigateToList(it))
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                playerOneHelper = BLANK_STRING_VALUE,
                                playerTwoHelper = BLANK_STRING_VALUE,
                                playerThreeHelper = BLANK_STRING_VALUE,
                                playerFourHelper = BLANK_STRING_VALUE,
                                partyNameError = validatePlayerNameUseCase
                                    .execute(uiState.value.partyName).errorMessage,
                                playerOneError = stringHelper
                                    .getString(R.string.message_error_validate_email_different),
                                playerTwoError = stringHelper
                                    .getString(R.string.message_error_validate_email_different),
                                playerThreeError = stringHelper
                                    .getString(R.string.message_error_validate_email_different),
                                playerFourError = stringHelper
                                    .getString(R.string.message_error_validate_email_different),
                            )
                        }
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

    private fun getNameByEmail(email: String): String =
        uiState.value.players[email] ?: stringHelper.getString(R.string.message_error_data_null)

    private fun checkErrorInFiledUiState(): Boolean {
        val resultName = validatePlayerNameUseCase.execute(uiState.value.partyName).successful
        val selectedPlayers = setOf<String>(
            uiState.value.playerOneName,
            uiState.value.playerTwoName,
            uiState.value.playerThreeName,
            uiState.value.playerFourName
        )
        val hasBlank = selectedPlayers.any { it == BLANK_STRING_VALUE }
        return (resultName && selectedPlayers.size == 4 && !hasBlank)
    }

    private suspend fun createNewPartyAndReturnId(): Int {
        val selectedPlayers = setOf<String>(
            uiState.value.playerOneName,
            uiState.value.playerTwoName,
            uiState.value.playerThreeName,
            uiState.value.playerFourName
        )
        return partyRepository.createNewPartyAndReturnId(selectedPlayers)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class NavigateToList(val idParty: Int) : UiEvent()
    }
}