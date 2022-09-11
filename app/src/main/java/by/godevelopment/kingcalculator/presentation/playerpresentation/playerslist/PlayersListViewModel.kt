package by.godevelopment.kingcalculator.presentation.playerpresentation.playerslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.playersdomain.models.PlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.GetListPlayerModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val getListPlayerModelUseCase: GetListPlayerModelUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<PlayersListUiEvent>()
    val uiEvent: Flow<PlayersListUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null
    private var reloadsNumber = 0

    init {
        fetchDataModel()
    }

    private fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getListPlayerModelUseCase()
                .onStart { _uiState.update { it.copy(isFetchingData = true) } }
                .catch { exception ->
                    Log.i(TAG, "viewModelScope.catch ${exception.message}")
                    _uiState.update { it.copy(isFetchingData = false) }
                    _uiEvent.send(
                        PlayersListUiEvent.ShowMessage(
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

    private fun reloadDataModel() {
        if (reloadsNumber > 3) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                reloadsNumber = 0
                _uiEvent.send(PlayersListUiEvent.NavigateToBackScreen)
            }
        } else {
            reloadsNumber++
            fetchDataModel()
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<PlayerModel> = listOf()
    )
}
