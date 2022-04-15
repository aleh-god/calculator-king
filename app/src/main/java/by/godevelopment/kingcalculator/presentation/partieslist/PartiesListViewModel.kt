package by.godevelopment.kingcalculator.presentation.partieslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.helpers.StringHelper
import by.godevelopment.kingcalculator.domain.models.DataModel
import by.godevelopment.kingcalculator.domain.models.ItemPartyModel
import by.godevelopment.kingcalculator.domain.usecases.EmptyParams
import by.godevelopment.kingcalculator.domain.usecases.GetDataUseCase
import by.godevelopment.kingcalculator.domain.usecases.GetListPartyModelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartiesListViewModel @Inject constructor(
    private val getListPartyModelUseCase: GetListPartyModelUseCase,
    private val stringHelper: StringHelper
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _uiEvent  = MutableSharedFlow<String>(0)
    val uiEvent: SharedFlow<String> = _uiEvent

    private var fetchJob: Job? = null

    init {
        fetchDataModel()
    }

    fun fetchDataModel() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            getListPartyModelUseCase.execute(EmptyParams)
                .onStart {
                    _uiState.value = UiState(
                        isFetchingData = true
                    )
                }
                .catch { exception ->
                    Log.i(TAG, "viewModelScope.catch ${exception.message}")
                    _uiState.value = UiState(
                        isFetchingData = false
                    )
                    delay(1000)
                    _uiEvent.emit(stringHelper.getString(R.string.alert_error_loading))
                }
                .collect {
                    _uiState.value = UiState(
                        isFetchingData = false,
                        dataList = it
                    )
                }
        }
    }

    fun setResultToList(dataModel: DataModel) {
        Log.i(TAG, "MainViewModel setResultToList $dataModel")
    }

    fun sendDataToRemote(dataModel: DataModel) {
        Log.i(TAG, "sendDataToRemote: $dataModel")
        viewModelScope.launch {
            try {
                val response = null
                Log.i(TAG, "sendDataToRemote: response = $response")
            } catch (e: Exception) {
                _uiEvent.emit(stringHelper.getString(R.string.alert_error_loading))
                Log.i(TAG, "sendDataToRemote: catch = ${e.message}")
            }
        }
    }

    data class UiState(
        val isFetchingData: Boolean = false,
        val dataList: List<ItemPartyModel> = listOf()
    )
}