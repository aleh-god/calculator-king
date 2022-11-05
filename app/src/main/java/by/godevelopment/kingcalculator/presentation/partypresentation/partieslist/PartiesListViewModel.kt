package by.godevelopment.kingcalculator.presentation.partypresentation.partieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.RELOAD_MAX_LIMIT
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.DeletePartyUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetAllActivePlayersCountUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetPartyModelItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartiesListViewModel @Inject constructor(
    private val getPartyModelItemsUseCase: GetPartyModelItemsUseCase,
    private val deletePartyUseCase: DeletePartyUseCase,
    private val getAllActivePlayersCountUseCase: GetAllActivePlayersCountUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PartiesListUiEvent>()
    val uiEvent: Flow<PartiesListUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null
    private var reloadsCount = 0

    init {
        fetchDataModel()
    }

    private fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getPartyModelItemsUseCase()
                .onStart { _uiState.update { it.copy(isFetchingData = true) } }
                .catch { _ ->
                    _uiState.update { it.copy(isFetchingData = false) }
                    _uiEvent.send(
                        PartiesListUiEvent.ShowMessage(
                            message = R.string.message_error_data_load,
                            textAction = R.string.snackbar_btn_reload,
                            onAction = ::reloadDataModel
                        )
                    )
                }
                .collect { list ->
                    _uiState.update { it.copy(isFetchingData = false, dataList = list) }
                }
        }
    }

    fun deleteParty(partyId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isFetchingData = true) }
            val deleteResult = deletePartyUseCase(partyId)
            when (deleteResult) {
                is ResultDataBase.Error -> _uiEvent.send(
                    PartiesListUiEvent.ShowMessage(
                        message = deleteResult.message,
                        textAction = R.string.snackbar_btn_reload,
                        onAction = ::reloadDataModel
                    )
                )
                is ResultDataBase.Success -> {
                    _uiEvent.send(
                        PartiesListUiEvent.ShowMessage(
                            message = R.string.message_delete_party_result,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = {}
                        )
                    )
                }
            }
            _uiState.update { it.copy(isFetchingData = false) }
        }
    }

    private fun reloadDataModel() {
        if (reloadsCount > RELOAD_MAX_LIMIT) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                reloadsCount = 0
                _uiEvent.send(
                    PartiesListUiEvent.ShowMessage(
                        message = R.string.message_error_bad_database,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = {}
                    )
                )
            }
        } else {
            reloadsCount++
            fetchDataModel()
        }
    }

    fun checkPlayersMinAndNavigate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isFetchingData = true) }
            val playersCount = getAllActivePlayersCountUseCase()
            when (playersCount) {
                is ResultDataBase.Error -> _uiEvent.send(
                    PartiesListUiEvent.ShowMessage(
                        message = playersCount.message,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = {}
                    )
                )
                is ResultDataBase.Success -> {
                    if (playersCount.value > 3)
                        _uiEvent.send(PartiesListUiEvent.NavigateToPartyAddForm)
                    else _uiEvent.send(
                        PartiesListUiEvent.ShowMessage(
                            message = R.string.message_error_validate_payers_count,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = {}
                        )
                    )
                }
            }
            _uiState.update { it.copy(isFetchingData = false) }
        }
    }

    fun checkPlayersIsActiveAndNavigateToPartyCard(partyId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isFetchingData = true) }
            _uiState.value.dataList.firstOrNull { it.id == partyId }?.let {
                if (it.player_one.isActive &&
                    it.player_two.isActive &&
                    it.player_three.isActive &&
                    it.player_four.isActive
                ) _uiEvent.send(PartiesListUiEvent.NavigateToPartyCard(partyId))
                else _uiEvent.send(
                    PartiesListUiEvent.ShowMessage(
                        message = R.string.message_error_validate_players_is_active,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = { reloadDataModel() }
                    )
                )
            }
            _uiState.update { it.copy(isFetchingData = false) }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<ItemPartyModel> = listOf()
    )
}
