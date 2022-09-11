package by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PartyInfoItemModel
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetGamesScoreByPartyIdUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetPartyNameUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetPlayersByPartyIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartyInfoViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getGamesScoreByPartyIdUseCase: GetGamesScoreByPartyIdUseCase,
    private val getPlayersByPartyIdUseCase: GetPlayersByPartyIdUseCase,
    private val getPartyNameUseCase: GetPartyNameUseCase
) : ViewModel() {

    val partyId = state.get<Long>("partyId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PartyInfoUiEvent>()
    val uiEvent: Flow<PartyInfoUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null
    private var reloadsNumber = 0

    init {
        fetchDataModel()
    }

    private fun reloadDataModel() {
        if (reloadsNumber > 3) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                reloadsNumber = 0
                _uiEvent.send(PartyInfoUiEvent.NavigateToBackScreen)
            }
        } else {
            reloadsNumber++
            fetchDataModel()
        }
    }

    private fun fetchDataModel() {
        partyId?.let {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                _uiState.update { it.copy(isFetchingData = true) }
                val partyName = async { getPartyNameUseCase(partyId) }
                val players = async { getPlayersByPartyIdUseCase(partyId) }
                val gamesScore = async { getGamesScoreByPartyIdUseCase(partyId) }
                updateUiState(partyName.await(), players.await(), gamesScore.await())
            }
        }
    }

    private suspend fun updateUiState(
        partyName: ResultDataBase<String>,
        players: ResultDataBase<PlayersInPartyModel>,
        gamesScore: ResultDataBase<List<PartyInfoItemModel>>
    ) {
        when (partyName) {
            is ResultDataBase.Error -> {
                _uiEvent.send(
                    PartyInfoUiEvent.ShowMessage(
                        message = partyName.message,
                        textAction = R.string.snackbar_btn_reload,
                        onAction = { reloadDataModel() }
                    )
                )
            }
            is ResultDataBase.Success -> {
                when (gamesScore) {
                    is ResultDataBase.Error -> {
                        _uiEvent.send(
                            PartyInfoUiEvent.ShowMessage(
                                message = gamesScore.message,
                                textAction = R.string.snackbar_btn_reload,
                                onAction = { reloadDataModel() }
                            )
                        )
                    }
                    is ResultDataBase.Success -> {
                        when (players) {
                            is ResultDataBase.Error -> {
                                _uiEvent.send(
                                    PartyInfoUiEvent.ShowMessage(
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
                                        partyName = partyName.value,
                                        dataList = gamesScore.value
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

    data class UiState(
        val isFetchingData: Boolean = false,
        val partyName: String = "",
        val playersInPartyModel: PlayersInPartyModel = PlayersInPartyModel(),
        val dataList: List<PartyInfoItemModel> = emptyList()
    )
}
