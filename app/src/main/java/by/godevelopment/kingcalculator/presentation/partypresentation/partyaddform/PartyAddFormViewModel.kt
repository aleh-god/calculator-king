package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.data.entities.PartyNote
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.commons.models.ValidationResult
import by.godevelopment.kingcalculator.domain.partiesdomain.repositories.PartyRepository
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.ValidatePartyNameUseCase
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.ValidatePlayersChoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyAddFormViewModel @Inject constructor(
    private val partyRepository: PartyRepository,
    private val stringHelper: StringHelper,
    private val validatePartyNameUseCase: ValidatePartyNameUseCase,
    private val validatePlayersChoiceUseCase: ValidatePlayersChoiceUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddPartyFormState> = MutableStateFlow(AddPartyFormState())
    val uiState: StateFlow<AddPartyFormState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(players = partyRepository.getAllPlayersIdToNames())
            }
        }
    }

    fun onEvent(event: AddPartyFormUserEvent) {
        when(event) {
            is AddPartyFormUserEvent.PartyNameChanged -> {
                val result = validatePartyNameUseCase.execute(event.partyName)
                _uiState.update { it.copy(
                    partyName = event.partyName,
                    partyNameError = result.errorMessage
                ) }
            }
            is AddPartyFormUserEvent.PlayerOneNameChanged -> {
                _uiState.update { it.copy(playerOneName = event.playerOneName) }
                setPlayerNameChoiceErrors()
            }
            is AddPartyFormUserEvent.PlayerTwoNameChanged -> {
                _uiState.update { it.copy(playerTwoName = event.playerTwoName) }
                setPlayerNameChoiceErrors()
            }
            is AddPartyFormUserEvent.PlayerThreeNameChanged -> {
                _uiState.update { it.copy(playerThreeName = event.playerThreeName) }
                setPlayerNameChoiceErrors()
            }
            is AddPartyFormUserEvent.PlayerFourNameChanged -> {
                _uiState.update { it.copy(playerFourName = event.playerFourName) }
                setPlayerNameChoiceErrors()
            }
            is AddPartyFormUserEvent.PressStartButton -> {
                createNewParty()
            }
        }
    }

    private fun createNewParty() {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch {
            _uiState.update { it.copy(showsProgress = true) }
            if(checkInputFieldsUiState()) {
                try {
                    val newParty = uiState.value.let {
                        PartyNote(
                            partyName = it.partyName,
                            startedAt = System.currentTimeMillis(),
                            playerOneId = it.players[it.playerOneName]!!,
                            playerTwoId = it.players[it.playerTwoName]!!,
                            playerThreeId = it.players[it.playerThreeName]!!,
                            playerFourId = it.players[it.playerFourName]!!
                        )
                    }
                    partyRepository.createNewPartyAndReturnId(newParty).let {
                        if (it < 0) {
                            _uiEvent.send(
                                UiEvent.ShowSnackbar(
                                    stringHelper.getString(R.string.message_error_data_save)
                                )
                            )
                        } else { _uiEvent.send(UiEvent.NavigateToList(it)) }
                    }
                } catch (e: Exception) {
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            stringHelper.getString(R.string.message_error_data_save)
                        )
                    )
                }
            } else {
                _uiEvent.send(
                    UiEvent.ShowSnackbar(
                        stringHelper.getString(R.string.message_error_players_info_empty)
                    )
                )
            }
            _uiState.update { it.copy(showsProgress = false) }
        }
    }

    private fun checkInputFieldsUiState(): Boolean {
        val partyNameCheck = validatePartyNameUseCase.execute(_uiState.value.partyName).successful
        val playerNamesCheck = runValidatePlayersChoice().successful
        return (partyNameCheck && playerNamesCheck)
    }

    private fun setPlayerNameChoiceErrors() {
        runValidatePlayersChoice().errorMessage.let {
            _uiState.update { state ->
                state.copy(
                    playerOneError = it,
                    playerTwoError = it,
                    playerThreeError = it,
                    playerFourError = it,
                )
            }
        }
    }

    private fun runValidatePlayersChoice(): ValidationResult {
        val playersChoice = listOf(
            _uiState.value.playerOneName,
            _uiState.value.playerTwoName,
            _uiState.value.playerThreeName,
            _uiState.value.playerFourName
        )
        return validatePlayersChoiceUseCase.execute(playersChoice)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class NavigateToList(val idParty: Long) : UiEvent()
    }
}