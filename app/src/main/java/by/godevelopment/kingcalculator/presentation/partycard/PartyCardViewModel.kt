package by.godevelopment.kingcalculator.presentation.partycard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class PartyCardViewModel @Inject constructor(
    state: SavedStateHandle
) : ViewModel() {

    val idPlayer = state.get<Int>("partyId")
    private var suspendJob: Job? = null

}