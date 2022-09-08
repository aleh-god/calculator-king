package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerInfoModel
import by.godevelopment.kingcalculator.domain.playersdomain.repositories.PlayerRepository
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.GetPlayerInfoListUseCase
import by.godevelopment.kingcalculator.presentation.playerpresentation.playerslist.PlayersListUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerInfoViewModel @Inject constructor(
    state: SavedStateHandle,
    private val getPlayerInfoListUseCase: GetPlayerInfoListUseCase,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val playerId = state.get<Long>("playerId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<PlayerInfoUiEvent>()
    val uiEvent: Flow<PlayerInfoUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null
    private var reloadsNumber = 0

    init {
        fetchDataModel()
    }

    private fun fetchDataModel() {
        playerId?.let {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                _uiState.update { it.copy(isFetchingData = true) }
                fetchPlayerName(it)
                fetchPlayerInfoList(it)
                _uiState.update { it.copy(isFetchingData = false) }
            }
        }
    }

    private suspend fun fetchPlayerName(playerId: Long) {
        val resultName = playerRepository.getPlayerById(playerId)
        when(resultName) {
            is ResultDataBase.Error -> { _uiEvent.send(PlayerInfoUiEvent.ShowMessage(
                message = resultName.message,
                textAction = R.string.snackbar_btn_reload,
                onAction = ::reloadDataModel
            ))
            }
            is ResultDataBase.Success -> {
                _uiState.update { it.copy(playerName = resultName.value.name) }
            }
        }
    }

    private suspend fun fetchPlayerInfoList(playerId: Long) {
        val resultInfo = getPlayerInfoListUseCase(playerId)
        when(resultInfo) {
            is ResultDataBase.Error -> { _uiEvent.send(PlayerInfoUiEvent.ShowMessage(
                message = resultInfo.message,
                textAction = R.string.snackbar_btn_reload,
                onAction = ::reloadDataModel
            ))
            }
            is ResultDataBase.Success -> {
                _uiState.update { it.copy(dataList = resultInfo.value) }
            }
        }
    }

    private fun reloadDataModel() {
        if (reloadsNumber > 3) {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch {
                reloadsNumber = 0
                _uiEvent.send(PlayerInfoUiEvent.NavigateToBackScreen)
            }
        }
        else {
            reloadsNumber++
            fetchDataModel()
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val playerName: String = "",
        val dataList: List<ItemPlayerInfoModel> = emptyList()
    )
}
