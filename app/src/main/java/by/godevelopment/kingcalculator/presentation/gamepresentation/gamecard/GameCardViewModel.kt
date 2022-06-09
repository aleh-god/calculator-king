package by.godevelopment.kingcalculator.presentation.gamepresentation.gamecard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameCardViewModel @Inject constructor(
    state: SavedStateHandle
) : ViewModel() {

//    val idPlayer = state.get<Long>("partyId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<String>()
    val uiEvent: Flow<String> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        load()
    }

    private fun load() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {

        }
    }

    data class UiState(
        val isFetchingData: Boolean = false
    )
}