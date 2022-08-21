package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerInfoModel
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.GetPlayerInfoListUseCase
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getPlayerInfoListUseCase: GetPlayerInfoListUseCase
) : ViewModel() {

    val playerId = state.get<Long>("idPlayer")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<Int>()
    val uiEvent: Flow<Int> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        fetchDataList()
    }

    private fun fetchDataList() {
        playerId?.let {
            fetchJob?.cancel()
            fetchJob = viewModelScope.launch(ioDispatcher) {
                _uiState.update { it.copy(isFetchingData = true) }
                val result = getPlayerInfoListUseCase(playerId)
                when(result) {
                    is ResultDataBase.Error -> { _uiEvent.send(result.message) }
                    is ResultDataBase.Success -> {
                        _uiState.update { it.copy(dataList = result.value) }
                    }
                }
                _uiState.update { it.copy(isFetchingData = false) }
            }
        }
    }

    fun reloadDataList() {
        // TODO("count reload and navigate to root")
        fetchDataList()
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<ItemPlayerInfoModel> = emptyList()
    )
}
