package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.gamesdomain.models.BodyItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.GetMultiItemModelsUseCase
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

    private val idGame = state.get<Long>("gameId")

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
                    _uiState.value = UiState(isFetchingData = true)
                    _uiState.value = UiState(
                        isFetchingData = false,
                        listMultiItems = getMultiItemModels.invoke(idGame)
                    )
                } catch (e: Exception) {
                    _uiState.value = UiState(isFetchingData = false)
                    viewModelScope.launch {
                        _uiEvent.send(e.message.toString())
                    }
                }
            }
        }
    }

    fun reloadDataModel() {
        load()
    }

    fun onClickDec(rowId: Int) {
        Log.i(TAG, "onClickDec: $rowId")
        val currentModel = _uiState.value.listMultiItems
            .first { it.rowId == rowId } as BodyItemModel
        if (currentModel.tricks == 0) return
        val newCount = currentModel.tricks - 1
        updateTricksStateById(rowId, newCount, currentModel)
    }

    fun onClickInc(rowId: Int) {
        Log.i(TAG, "onClickInc: $rowId")
        val currentModel = _uiState.value.listMultiItems
            .first { it.rowId == rowId } as BodyItemModel
        if (currentModel.tricks == currentModel.gameType.tricksCount) return
        val newCount = currentModel.tricks + 1
        updateTricksStateById(rowId, newCount, currentModel)
    }

    fun onClickEdit(rowId: Int) {
        Log.i(TAG, "onChangeEdit: $rowId")
//        val currentModel = _uiState.value.listMultiItems
//            .first { it.rowId == rowId } as BodyItemModel
//        val newCount = when {
//            currentModel.totalTricks < 0 -> 0
//            currentModel.totalTricks > currentModel.gameType.tricksCount -> currentModel.gameType.tricksCount
//            else -> count
//        }
//        updateTricksStateById(rowId, newCount, currentModel)
    }

    private fun updateTricksStateById(rowId: Int, newCount: Int, currentModel: BodyItemModel) {

        val newTotalScore = currentModel.gameType.getTotalGameScore(newCount)
        Log.i(TAG, "updateTricksStateById: rowId= $rowId\n currentModel = $currentModel\n newCount = $newCount \n newTotalScore = $newTotalScore")

        _uiState.update {
            val newList = it.listMultiItems
                .map { multiItemModel ->
                if(multiItemModel.rowId == rowId) {
                    currentModel.copy(
                        tricks = newCount,
                        score = newTotalScore
                    )
                }
                else multiItemModel
            }
            Log.i(TAG, "_uiState.update: $newList")
            it.copy(listMultiItems = newList)
        }
    }

    fun saveGameData() {
        Log.i(TAG, "saveGameData: invoke")
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val gameTotalScore: Int = 0,
        val listMultiItems: List<MultiItemModel> = listOf()
    )
}