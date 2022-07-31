package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.BODY_ROW_TYPE
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.gamesdomain.models.MultiItemModel
import by.godevelopment.kingcalculator.domain.gamesdomain.models.Players
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.GetMultiItemModelsUseCase
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.GetPartyIdByGameIdUseCase
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.SaveGameUseCase
import by.godevelopment.kingcalculator.domain.gamesdomain.usecases.ValidatePlayersScoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class GameAddFormViewModel @Inject constructor(
    state: SavedStateHandle,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getMultiItemModels: GetMultiItemModelsUseCase,
    private val validatePlayersScoreUseCase: ValidatePlayersScoreUseCase,
    private val getPartyIdByGameIdUseCase: GetPartyIdByGameIdUseCase,
    private val saveGameUseCase: SaveGameUseCase
) : ViewModel() {

    private val gameId: Long? = state.get<Long>("gameId")

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<GameAddFormUiEvent>()
    val uiEvent: Flow<GameAddFormUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        fetchDataModel()
        Log.i(TAG, "GameAddFormViewModel: gameId = $gameId")
    }

    private fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(ioDispatcher) {
            try {
                _uiState.update { it.copy(isFetchingData = true) }
                val list = getMultiItemModels(gameId = gameId ?: throw NullPointerException())
                Log.i(TAG, "GameAddFormViewModel.fetchDataModel: $gameId = $list")
                _uiState.update { it.copy(listMultiItems = list) }
            }
            catch (e: Exception) {
                Log.i(TAG, "fetchDataModel.catch: ${e.message}")
                _uiEvent.send(
                    GameAddFormUiEvent.ShowMessageUiEvent(
                        message = R.string.message_error_data_load,
                        onAction = ::reloadDataModel
                    )
                )
            }
            finally { _uiState.update { it.copy(isFetchingData = false) } }
        }
    }

    private fun reloadDataModel() {
        fetchDataModel()
    }

    fun onClickDec(rowId: Int) {
        val currentModel = _uiState.value.listMultiItems.first { it.rowId == rowId }
        if (currentModel.tricks == 0) return
        val newCount = currentModel.tricks - 1
        updateTricksStateById(rowId, newCount, currentModel)
    }

    fun onClickInc(rowId: Int) {
        val currentModel = _uiState.value.listMultiItems.first { it.rowId == rowId }
        if (currentModel.tricks == currentModel.gameType.tricksCount) return
        val newCount = currentModel.tricks + 1
        updateTricksStateById(rowId, newCount, currentModel)
    }

    fun onClickEdit(rowId: Int, newCount: Int) {
        val currentModel = _uiState.value.listMultiItems.first { it.rowId == rowId }
        val currentCount = when {
            newCount < 0 -> 0
            newCount > currentModel.gameType.tricksCount -> currentModel.gameType.tricksCount
            else -> newCount
        }
        updateTricksStateById(rowId, currentCount, currentModel)
    }

    private fun updateTricksStateById(rowId: Int, newCount: Int, currentModel: MultiItemModel) {
        val currentGameType = currentModel.gameType
        val newScore = currentGameType.getTotalGameScore(newCount)
        _uiState.update { state ->
            var newList = state.listMultiItems
                .map { multiItemModel ->
                    when {
                        multiItemModel.gameType == currentGameType
                                && multiItemModel.rowId == rowId -> {
                            currentModel.copy(
                                tricks = newCount,
                                score = newScore,
                                hasError = false
                            )
                        }
                        multiItemModel.gameType == currentGameType
                                && multiItemModel.rowId != rowId -> {
                            multiItemModel.copy(hasError = false)
                        }
                        else -> multiItemModel
                    }
                }

            val scoreList = Players.values()
                .toList()
                .sortedBy { it.id }
                .map {
                    newList.sumOf { item ->
                        if(item.itemViewType == BODY_ROW_TYPE && item.playerNumber == it) item.score
                        else 0
                    }
                }

            newList = newList.map {
                when(it.playerNumber) {
                    Players.PlayerOne -> {
                        it.copy(totalPlayerScore = scoreList[0])
                    }
                    Players.PlayerTwo -> {
                        it.copy(totalPlayerScore = scoreList[1])
                    }
                    Players.PlayerThree -> {
                        it.copy(totalPlayerScore = scoreList[2])
                    }
                    Players.PlayerFour -> {
                        it.copy(totalPlayerScore = scoreList[3])
                    }
                }
            }

            val gameTotalScore = newList
                .filter { it.itemViewType == BODY_ROW_TYPE }
                .sumOf { it.score }

            state.copy(
                listMultiItems = newList,
                gameTotalScore = gameTotalScore
            )
        }
    }

    fun saveGameData() {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isFetchingData = true) }
            val result = validatePlayersScoreUseCase.invoke(_uiState.value.listMultiItems)
            Log.i(TAG, "saveGameData: result = $result")
            if (result.successful) {
                try {
                    saveGameUseCase(
                        gameId = gameId ?: throw NullPointerException(),
                        items = _uiState.value.listMultiItems
                    )
                    when(val idResult = getPartyIdByGameIdUseCase(gameId)) {
                        is ResultDataBase.Error -> {
                            throw IllegalStateException() // TODO("change exc to error")
                        }
                        is ResultDataBase.Success -> {
                            _uiEvent.send(
                                GameAddFormUiEvent.NavigateToPartyCardUiEvent(idResult.value)
                            )
                        }
                    }
                }
                catch (e: Exception) {
                    Log.i(TAG, "saveGameData.catch: ${e.message}")
                    _uiEvent.send(
                        GameAddFormUiEvent.ShowMessageUiEvent(
                            message = R.string.message_error_data_save,
                            onAction = { }
                        ))
                }
                finally { _uiState.update { it.copy(isFetchingData = false) } }
            } else {
                _uiState.update {
                    it.copy(
                        isFetchingData = false,
                        listMultiItems = result.errorList ?: it.listMultiItems
                    )
                }
                _uiEvent.send(
                    GameAddFormUiEvent.ShowMessageUiEvent(
                        message = R.string.message_error_input_values,
                        onAction = { }
                    ))
            }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val gameTotalScore: Int = 0,
        val listMultiItems: List<MultiItemModel> = listOf()
    )
}
