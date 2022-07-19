package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.GetMultiItemModelsUseCase
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.GetPlayersByGameIdUseCase
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.SaveGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameAddFormViewModel @Inject constructor(
    state: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getMultiItemModels: GetMultiItemModelsUseCase,
    private val saveGameUseCase: SaveGameUseCase,
) : ViewModel() {

    val idGame = state.get<Long>("gameId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<String>()
    val uiEvent: Flow<String> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        load()
        Log.i(TAG, "GameAddFormViewModel: = $idGame")
    }

    private fun load() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(ioDispatcher) {
            idGame?.let {
                try {
                    _uiState.value = UiState(
                        isFetchingData = true,
                        gameTotalScore = "Loading data..."
                    )
                    _uiState.value = UiState(
                        isFetchingData = false,
                        gameTotalScore = "Total Game Score = ",
                        listMultiItems = getMultiItemModels.invoke(idGame)
                    )
                } catch (e: Exception) {
                    _uiState.value = UiState(
                        isFetchingData = false,
                        gameTotalScore = "${e.message}",
                        listMultiItems = getMultiItemModels.invoke(idGame)
                    )
                }
            }
        }
    }

    fun fetchDataModel() {
        load()
    }

    fun saveGameData() {
        Log.i(TAG, "saveGameData: invoke")
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val gameTotalScore:String = "",
        val listMultiItems: List<MultiItemModel> = listOf()
    )
}