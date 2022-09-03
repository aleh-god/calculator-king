package by.godevelopment.kingcalculator.presentation.settingsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteGamesRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeletePartiesRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeletePlayersRepository
import by.godevelopment.kingcalculator.domain.settingsdomain.repositories.DeleteTricksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val deletePartiesRepository: DeletePartiesRepository,
    private val deleteGamesRepository: DeleteGamesRepository,
    private val deleteTricksRepository: DeleteTricksRepository,
    private val deletePlayersRepository: DeletePlayersRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState("loading"))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<SettingsUiEvent>()
    val uiEvent: Flow<SettingsUiEvent> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    fun deleteAllParties() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isProgress = true) }
            deleteParties()
            _uiState.update { it.copy(isProgress = false) }
        }
    }

    fun deleteAllGames() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isProgress = true) }
            deleteParties()
            deleteGames()
            deleteTricks()
            _uiState.update { it.copy(isProgress = false) }
        }
    }

    fun deleteAll() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            _uiState.update { it.copy(isProgress = true) }
            deleteParties()
            deleteGames()
            deleteTricks()
            deletePlayers()
            _uiState.update { it.copy(isProgress = false) }
        }
    }

    private suspend fun deleteParties() {
            val resultParties: ResultDataBase<Int> = deletePartiesRepository.deleteAllParties()
            when(resultParties) {
                is ResultDataBase.Error -> {
                    _uiEvent.send(
                        SettingsUiEvent.ShowMessage(
                            message = resultParties.message,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = {}
                        ))
                }
                is ResultDataBase.Success -> {
                    _uiEvent.send(
                        SettingsUiEvent.ShowMessage(
                            message = R.string.message_delete_notes_result,
                            valueForText = resultParties.value,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = { }
                        ))
                }
            }
    }

    private suspend fun deleteGames() {
            val resultGames: ResultDataBase<Int> = deleteGamesRepository.deleteAllGames()
            when(resultGames) {
                is ResultDataBase.Error -> {
                    _uiEvent.send(
                        SettingsUiEvent.ShowMessage(
                            message = resultGames.message,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = {}
                        ))
                }
                is ResultDataBase.Success -> {
                    _uiEvent.send(
                        SettingsUiEvent.ShowMessage(
                            message = R.string.message_delete_notes_result,
                            valueForText = resultGames.value,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = { }
                        ))
                }
            }
    }

    private suspend fun deleteTricks() {
            val resultTricks: ResultDataBase<Int> = deleteTricksRepository.deleteAllTricks()
            when(resultTricks) {
                is ResultDataBase.Error -> { _uiEvent.send(
                    SettingsUiEvent.ShowMessage(
                        message = resultTricks.message,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = {}
                    ))
                }
                is ResultDataBase.Success -> {
                    _uiEvent.send(
                        SettingsUiEvent.ShowMessage(
                            message = R.string.message_delete_notes_result,
                            valueForText = resultTricks.value,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = { }
                        ))
                }
            }
    }

    private suspend fun deletePlayers() {
            val resultPlayers: ResultDataBase<Int> = deletePlayersRepository.deleteAllPlayers()
            when(resultPlayers) {
                is ResultDataBase.Error -> { _uiEvent.send(
                    SettingsUiEvent.ShowMessage(
                        message = resultPlayers.message,
                        textAction = R.string.snackbar_btn_neutral_ok,
                        onAction = {}
                    ))
                }
                is ResultDataBase.Success -> {
                    _uiEvent.send(
                        SettingsUiEvent.ShowMessage(
                            message = R.string.message_delete_notes_result,
                            valueForText = resultPlayers.value,
                            textAction = R.string.snackbar_btn_neutral_ok,
                            onAction = { }
                        ))
                }
            }
    }

    data class UiState(
        val badString: String?,
        val isProgress: Boolean = false
    )
}
