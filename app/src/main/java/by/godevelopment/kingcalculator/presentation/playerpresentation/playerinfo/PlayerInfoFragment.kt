package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import by.godevelopment.kingcalculator.databinding.FragmentPlayerInfoBinding
import by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo.PartyInfoFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PlayerInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerInfoFragment()
    }

    private val viewModel: PlayerInfoViewModel by viewModels()
    private var _binding: FragmentPlayerInfoBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
        setupUi()
        setupEvent()
        return binding.root
    }

    private fun setupUi() = with(binding) {
        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                if (!uiState.isFetchingData) progress.visibility = View.GONE
                else progress.visibility = View.VISIBLE
                playerName.text = uiState.playerName
                infoList.adapter = InfoAdapter(uiState.dataList)
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is PlayerInfoUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PartyInfoFragmentDirections
                                .actionPartyInfoFragmentToPartiesListFragment()
                        )
                    }
                    is PlayerInfoUiEvent.ShowMessage -> {
                        snackbar?.dismiss()
                        snackbar = Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(event.textAction) { event.onAction() }
                        snackbar?.show()
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    override fun onStop() {
        snackbar?.dismiss()
        snackbar = null
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
