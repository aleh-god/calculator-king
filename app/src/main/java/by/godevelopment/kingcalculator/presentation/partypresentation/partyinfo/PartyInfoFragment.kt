package by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo

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
import by.godevelopment.kingcalculator.databinding.FragmentPartyInfoBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PartyInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PartyInfoFragment()
    }

    private val viewModel: PartyInfoViewModel by viewModels()
    private var _binding: FragmentPartyInfoBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyInfoBinding.inflate(inflater, container, false)
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
                        else binding.progress.visibility = View.VISIBLE
                        partyName.text = uiState.partyName
                        gameCollumOne.text = uiState.playersInPartyModel.playerOne
                        gameCollumTwo.text = uiState.playersInPartyModel.playerTwo
                        gameCollumThree.text = uiState.playersInPartyModel.playerThree
                        gameCollumFour.text = uiState.playersInPartyModel.playerFour
                        infoList.adapter = PartyInfoAdapter(uiState.dataList)
                    }
            }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is PartyInfoUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PartyInfoFragmentDirections
                                .actionPartyInfoFragmentToPartiesListFragment()
                        )
                    }
                    is PartyInfoUiEvent.ShowMessage -> {
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
