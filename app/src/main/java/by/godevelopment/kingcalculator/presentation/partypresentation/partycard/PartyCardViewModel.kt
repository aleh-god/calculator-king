package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.PARTY_ID_NAVIGATION_ARGUMENT
import by.godevelopment.kingcalculator.commons.RELOAD_MAX_LIMIT
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyCardViewModel @Inject constructor(
    private val checkUnfinishedGameInPartyUseCase: CheckUnfinishedGameInPartyUseCase,
    private val getGamesByPartyIdUseCase: GetGamesByPartyIdUseCase,
    private val getContractorPlayerByPartyIdUseCase: GetContractorPlayerByPartyIdUseCase,
    private val getPlayersByPartyIdUseCase: GetPlayersByPartyIdUseCase,
    private val createGameNoteUseCase: CreateGameNoteUseCase,
    state: SavedStateHandle
) : ViewModel() {

    val partyId = state.get<Long>(PARTY_ID_NAVIGATION_ARGUMENT)

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PartyCardUiEvent>()
    val uiEvent: Flow<PartyCardUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null
    private var reloadsCount = 0

    fun checkUnfinishedGame() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (partyId != null) {
                val checkResult = checkUnfinishedGameInPartyUseCase(partyId)
                when (checkResult) {
                    is ResultDataBase.Error -> {
                        _uiEvent.send(
                            PartyCardUiEvent.ShowMessage(
                                message = checkResult.message,
                                textAction = R.string.snackbar_btn_neutral_ok,
                                onAction = { }
                            )
                        )
                    }
                    is ResultDataBase.Success -> {
                        if (checkResult.value != null) {
                            _uiEvent.send(PartyCardUiEvent.NavigateToGameAddForm(checkResult.value))
                        } else {
                            fetchDataModel()
                        }
                    }
                }
            } else {
                _uiEvent.send(
                    PartyCardUiEvent.ShowMessage(
                        message = R.string.message_error_data_load,
                        textAction = R.string.snackbar_btn_reload,
                        onAction = ::reloadDataModel
                    )
                )
            }
        }
    }

    private fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            if (partyId != null) {
                _uiState.update { it.copy(isFetchingData = true) }
                val contractorPlayer = async { getContractorPlayerByPartyIdUseCase(partyId) }
                val players = async { getPlayersByPartyIdUseCase(partyId) }
                val gamesList = async { getGamesByPartyIdUseCase(partyId) }
                updateUiState(contractorPlayer.await(), players.await(), gamesList.await())
            } else {
                _uiState.update { it.copy(isFetchingData = false) }
                _uiEvent.send(
                    PartyCardUiEvent.ShowMessage(
                        message = R.string.message_error_data_load,
                        textAction = R.string.snackbar_btn_reload,
                        onAction = ::reloadDataModel
                    )
                )
            }
        }
    }

    private suspend fun updateUiState(
        contractorPlayer: ResultDataBase<String>,
        players: ResultDataBase<PlayersInPartyModel>,
        gamesList: ResultDataBase<List<GamesTableItemModel>>
    ) {
        when (contractorPlayer) {
            is ResultDataBase.Error -> {
                _uiEvent.send(
                    PartyCardUiEvent.ShowMessage(
                        message = contractorPlayer.message,
                        textAction = R.string.snackbar_btn_reload,
                        onAction = { reloadDataModel() }
                    )
                )
            }
            is ResultDataBase.Success -> {
                when (gamesList) {
                    is ResultDataBase.Error -> {
                        _uiEvent.send(
                            PartyCardUiEvent.ShowMessage(
                                message = gamesList.message,
                                textAction = R.string.snackbar_btn_reload,
                                onAction = { reloadDataModel() }
                            )
                        )
                    }
                    is ResultDataBase.Success -> {
                        when (players) {
                            is ResultDataBase.Error -> {
                                _uiEvent.send(
                                    PartyCardUiEvent.ShowMessage(
                                        message = players.message,
                                        textAction = R.string.snackbar_btn_reload,
                                        onAction = { reloadDataModel() }
                                    )
                                )
                            }
                            is ResultDataBase.Success -> {
                                _uiState.update {
                                    it.copy(
                                        isFetchingData = false,
                                        playersInPartyModel = players.value,
                                        contractorPlayer = contractorPlayer.value,
                                        dataList = gamesList.value
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        _uiState.update { it.copy(isFetchingData = false) }
    }

    private fun reloadDataModel() {
        if (reloadsCount > RELOAD_MAX_LIMIT) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                reloadsCount = 0
                _uiEvent.send(PartyCardUiEvent.NavigateToBackScreen)
            }
        } else {
            reloadsCount++
            fetchDataModel()
        }
    }

    fun createGameNote(gameType: GameType) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                _uiState.update { it.copy(isFetchingData = true) }
                val gameResult = createGameNoteUseCase(
                    gameType = gameType,
                    partyId = partyId ?: throw NullPointerException()
                )
                when (gameResult) {
                    is ResultDataBase.Error -> {
                        _uiEvent.send(
                            PartyCardUiEvent.ShowMessage(
                                message = R.string.message_error_data_save,
                                textAction = R.string.snackbar_btn_neutral_ok,
                                onAction = { }
                            )
                        )
                    }
                    is ResultDataBase.Success -> {
                        _uiEvent.send(PartyCardUiEvent.NavigateToGameAddForm(gameResult.value))
                    }
                }
            } catch (e: Exception) {
                _uiEvent.send(
                    PartyCardUiEvent.ShowMessage(
                        message = R.string.message_error_data_save,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = { }
                    )
                )
            } finally { _uiState.update { it.copy(isFetchingData = false) } }
        }
    }

    data class UiState(
        val contractorPlayer: String = "",
        val playersInPartyModel: PlayersInPartyModel = PlayersInPartyModel(),
        val dataList: List<GamesTableItemModel> = emptyList(),
        val isFetchingData: Boolean = false
    )
}
