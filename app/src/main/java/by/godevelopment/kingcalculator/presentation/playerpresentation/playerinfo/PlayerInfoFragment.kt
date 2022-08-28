package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPlayerInfoBinding
import by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo.PartyInfoFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerInfoFragment()
    }

    private val viewModel: PlayerInfoViewModel by viewModels()
    private var _binding: FragmentPlayerInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupUi(it)
            setupEvent(it)
        }
        return binding.root
    }

    private fun setupUi(lifecycle: Lifecycle) {
        binding.apply {
            infoList.layoutManager = LinearLayoutManager(requireContext())
            lifecycle.coroutineScope.launch {
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .collect { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else progress.visibility = View.VISIBLE
                        infoList.adapter = InfoAdapter(uiState.dataList)
                    }
            }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when(event) {
                    is PlayerInfoUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PartyInfoFragmentDirections
                                .actionPartyInfoFragmentToPartiesListFragment()
                        )
                    }
                    is PlayerInfoUiEvent.ShowMessage -> {
                        Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(event.textAction)
                            { event.onAction() }
                            .show()
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
