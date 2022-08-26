package by.godevelopment.kingcalculator.presentation.partypresentation.partieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.di.IoDispatcher
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import by.godevelopment.kingcalculator.domain.partiesdomain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.DeletePartyUseCase
import by.godevelopment.kingcalculator.domain.partiesdomain.usecases.GetPartyModelItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartiesListViewModel @Inject constructor(
    private val getPartyModelItemsUseCase: GetPartyModelItemsUseCase,
    private val deletePartyUseCase: DeletePartyUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEvent  = Channel<Int>()
    val uiEvent: Flow<Int> = _uiEvent.receiveAsFlow()

    private var fetchJob: Job? = null

    init {
        fetchDataModel()
    }

    private fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getPartyModelItemsUseCase()
                .onStart { _uiState.update { it.copy(isFetchingData = true) } }
                .catch { exception ->
                    Log.i(TAG, "PartiesListViewModel viewModelScope.catch ${exception.message}")
                    _uiState.update { it.copy(isFetchingData = false) }
                    _uiEvent.send(R.string.message_error_data_load)
                }
                .collect { list ->
                    _uiState.update { it.copy(isFetchingData = false, dataList = list) }
                }
        }
    }

    fun deleteParty(partyId: Long) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update { it.copy(isFetchingData = true) }
            val deleteResult = deletePartyUseCase(partyId)
            when(deleteResult) {
                is ResultDataBase.Error -> _uiEvent.send(deleteResult.message)
                is ResultDataBase.Success -> {
                    _uiEvent.send(R.string.message_delete_party_result)
                }
            }
            _uiState.update { it.copy(isFetchingData = false) }
        }
    }

    fun onAction() {
        fetchDataModel()
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<ItemPartyModel> = listOf()
    )
}
