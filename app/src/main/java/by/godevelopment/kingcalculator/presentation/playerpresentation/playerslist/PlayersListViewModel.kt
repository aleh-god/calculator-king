package by.godevelopment.kingcalculator.presentation.playerpresentation.playerslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.playersdomain.models.ItemPlayerModel
import by.godevelopment.kingcalculator.domain.playersdomain.usecases.GetListPlayerModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val getListPlayerModelUseCase: GetListPlayerModelUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<String>()
    val uiEvent: Flow<String> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        fetchDataModel()
    }

    fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getListPlayerModelUseCase()
                .onStart { _uiState.update { it.copy(isFetchingData = true) } }
                .catch { exception ->
                    Log.i(TAG, "viewModelScope.catch ${exception.message}")
                    _uiState.update { it.copy(isFetchingData = false) }
                    _uiEvent.send(stringHelper.getString(R.string.alert_error_loading))
                }
                .collect { list ->
                    _uiState.update { it.copy(isFetchingData = false, dataList = list) }
                }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<ItemPlayerModel> = listOf()
    )
}