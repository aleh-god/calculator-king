package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.commons.models.TestItem
import by.godevelopment.kingcalculator.domain.partiesdomain.models.PlayersInPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetContractorPlayerByPartyIdUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetGamesByPartyIdUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetPlayersByPartyIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val stringHelper: StringHelper,
    state: SavedStateHandle
) : ViewModel() {

    private val idParty = state.get<Long>("partyId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<String>()
    val uiEvent: Flow<String> = _uiEvent.receiveAsFlow()

    private var suspendJob: Job? = null

    init {
        Log.i(TAG, "PartyCardViewModel: $idParty")
        fetchDataModel()
    }

    fun fetchDataModel() {
        suspendJob?.cancel()
        suspendJob = viewModelScope.launch {
            if (idParty != null) {
                launch { fetchContractorPlayer(idParty) }
                launch { fetchDataList(idParty) }
                launch { fetchPlayers(idParty) }
            } else {
                _uiEvent.send(stringHelper.getString(R.string.alert_error_loading))
            }
        }
    }

    private suspend fun fetchDataList(idParty: Long) {
        getGamesByPartyIdUseCase(idParty)
            .onStart { _uiState.update { it.copy(isFetchingData = true) } }
            .catch { exception ->
                Log.i(TAG, "PartyCardViewModel fetchDataList.catch ${exception.message}")
                _uiState.update { it.copy(isFetchingData = false) }
                _uiEvent.send(stringHelper.getString(R.string.alert_error_loading))
            }
            .collect { list ->
                _uiState.update { it.copy(isFetchingData = false, dataList = list) }
            }
    }

    private suspend fun fetchPlayers(idParty: Long) {
        try {
            _uiState.update { it.copy(playersInPartyModel = getPlayersByPartyIdUseCase(idParty)) }
        } catch (e: Exception) {
            Log.i(TAG, "PartyCardViewModel fetchPlayers.catch ${e.message}")
            _uiEvent.send(stringHelper.getString(R.string.alert_error_loading))
        }
    }

    private suspend fun fetchContractorPlayer(idParty: Long) {
        try {
            _uiState.update {
                it.copy(contractorPlayer = getContractorPlayerByPartyIdUseCase(idParty))
            }
        } catch (e: Exception) {
            Log.i(TAG, "PartyCardViewModel fetchContractorPlayer.catch ${e.message} ")
            _uiEvent.send(stringHelper.getString(R.string.alert_error_loading))
        }
    }

    data class UiState(
        val contractorPlayer: String = "",
        val playersInPartyModel:PlayersInPartyModel = PlayersInPartyModel(),
        val dataList: List<TestItem> = emptyList(),
        val isFetchingData: Boolean = false
    )

}