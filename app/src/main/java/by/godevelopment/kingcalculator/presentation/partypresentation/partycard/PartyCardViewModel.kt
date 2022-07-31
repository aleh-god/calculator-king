package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.GamesTableItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.CreateGameNoteUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetContractorPlayerByPartyIdUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetGamesByPartyIdUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetPlayersByPartyIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyCardViewModel @Inject constructor(
    private val getGamesByPartyIdUseCase: GetGamesByPartyIdUseCase,
    private val getContractorPlayerByPartyIdUseCase: GetContractorPlayerByPartyIdUseCase,
    private val getPlayersByPartyIdUseCase: GetPlayersByPartyIdUseCase,
    private val createGameNoteUseCase: CreateGameNoteUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    state: SavedStateHandle
) : ViewModel() {

    val partyId = state.get<Long>("partyId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<PartyCardUiEvent>()
    val uiEvent: Flow<PartyCardUiEvent> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    init {
        Log.i(TAG, "PartyCardViewModel: $partyId")
        fetchDataModel()
    }

    private fun fetchDataModel() {
        suspendJob?.cancel()
        try {
            suspendJob = viewModelScope.launch(ioDispatcher) {
                if (partyId != null) {
                    _uiState.update { it.copy(isFetchingData = true) }
                    // TODO("rework to async await")
                    launch { fetchContractorPlayer(partyId) }
                    launch { fetchPlayers(partyId) }
                    launch { fetchGamesList(partyId) }
                }
                else {
                    _uiState.update { it.copy(isFetchingData = false) }
                    _uiEvent.send(
                        PartyCardUiEvent.ShowMessage(
                            message = R.string.message_error_data_load,
                            onAction = ::reloadDataModel
                        )
                    )
                }
            }
        }
        catch (e: Exception) {
            Log.i(TAG, "PartyCardViewModel fetchPlayers.catch ${e.message}")
            viewModelScope.launch {
                _uiState.update { it.copy(isFetchingData = false) }
                _uiEvent.send(
                    PartyCardUiEvent.ShowMessage(
                        message = R.string.message_error_data_load,
                        onAction = ::reloadDataModel
                    )
                )
            }
        }
    }

    private suspend fun fetchGamesList(partyId: Long) {
        getGamesByPartyIdUseCase(partyId).collect { list ->
            _uiState.update {
                it.copy(
                    isFetchingData = false,
                    dataList = list
                )
            }
        }
    }

    private suspend fun fetchPlayers(partyId: Long) {
        _uiState.update { it.copy(playersInPartyModel = getPlayersByPartyIdUseCase(partyId)) }
    }

    private suspend fun fetchContractorPlayer(partyId: Long) {
        _uiState.update { it.copy(contractorPlayer = getContractorPlayerByPartyIdUseCase(partyId)) }
    }

    private fun reloadDataModel() {
        fetchDataModel()
    }

    fun createGameNote(gameType: GameType) {
        Log.i(TAG, "createGameNote: gameType = $gameType partyId = $partyId")
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch(ioDispatcher) {
            try {
                _uiState.update { it.copy(isFetchingData = true) }
                val gameResult = createGameNoteUseCase(
                    gameType = gameType,
                    partyId = partyId ?: throw NullPointerException()
                )
                when(gameResult) {
                    is ResultDataBase.Error -> {
                        _uiEvent.send(PartyCardUiEvent.ShowMessage(
                            message = R.string.message_error_data_save,
                            onAction = { }
                        ))
                    }
                    is ResultDataBase.Success -> {
                        _uiEvent.send(PartyCardUiEvent.NavigateToPartyCard(gameResult.value))
                    }
                }
            } catch (e: Exception) {
                Log.i(TAG, "PartyCardViewModel createGameNote.catch ${e.message} ")
                _uiEvent.send(PartyCardUiEvent.ShowMessage(
                    message = R.string.message_error_data_save,
                    onAction = { }
                )
                )
            }
            finally { _uiState.update { it.copy(isFetchingData = false) } }
        }
    }

    data class UiState(
        val contractorPlayer: String = "",
        val playersInPartyModel:PlayersInPartyModel = PlayersInPartyModel(),
        val dataList: List<GamesTableItemModel> = emptyList(),
        val isFetchingData: Boolean = false
    )
}
