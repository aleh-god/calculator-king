package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPartyAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PartyAddFormFragment : Fragment() {

    companion object {
        fun newInstance() = PartyAddFormFragment()
    }

    private val viewModel: PartyAddFormViewModel by viewModels()
    private var _binding: FragmentPartyAddFormBinding? = null
    private val binding: FragmentPartyAddFormBinding
        get() = _binding!!
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // TODO("Fix ScrollView")
        _binding = FragmentPartyAddFormBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupUi(it)
            setupEvent(it)
        }
        setupListeners()
        return binding.root
    }

    private fun setupUi(lifecycle: Lifecycle) {
        lifecycle.coroutineScope.launch {
            viewModel.uiState
                .flowWithLifecycle(lifecycle)
                .collect { uiState ->
                    showProgressUi(uiState.showsProgress)
                    binding.apply {
                        partyName.error = if (uiState.partyNameError != null) getString(uiState.partyNameError)
                        else null
                        playerOneMenu.error = if (uiState.playerOneError != null)  getString(uiState.playerOneError)
                        else null
                        playerTwoMenu.error = if (uiState.playerTwoError != null)  getString(uiState.playerTwoError)
                        else null
                        playerThreeMenu.error = if (uiState.playerThreeError != null)  getString(uiState.playerThreeError)
                        else null
                        playerFourMenu.error = if (uiState.playerFourError != null) getString(uiState.playerFourError)
                        else null

                        ArrayAdapter(
                            requireContext(),
                            R.layout.menu_item,
                            uiState.players.keys.toTypedArray()
                        ).also {
                            playerTwoInputMenu.setAdapter(it)
                            playerOneInputMenu.setAdapter(it)
                            playerThreeInputMenu.setAdapter(it)
                            playerFourInputMenu.setAdapter(it)
                        }
                    }
                }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->

                when(event) {
                    is PartyAddFormUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PartyAddFormFragmentDirections
                                .actionPartyAddFormFragmentToPartiesListFragment()
                        )
                    }
                    is PartyAddFormUiEvent.ShowMessage -> {
                        snackbar?.dismiss()
                        snackbar = Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(event.textAction)
                            { event.onAction() }
                        snackbar?.show()
                    }
                    is PartyAddFormUiEvent.NavigateToList -> {
                        navigateToPartyCard(event.idParty)
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupListeners() {
        binding.bttnStart.setOnClickListener {
            viewModel.onEvent(AddPartyFormUserEvent.PressStartButton)
        }
        binding.partyNameEdit.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PartyNameChanged(it.toString()))
        }
        binding.playerOneInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerOneNameChanged(it.toString()))
        }
        binding.playerTwoInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerTwoNameChanged(it.toString()))
        }
        binding.playerThreeInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerThreeNameChanged(it.toString()))
        }
        binding.playerFourInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerFourNameChanged(it.toString()))
        }
    }

    private fun showProgressUi(key: Boolean) {
        binding.apply {
            if (key) {
                progress.visibility = View.VISIBLE
                bttnStart.visibility = View.GONE
            } else {
                progress.visibility = View.GONE
                bttnStart.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateToPartyCard(idParty: Long) {
        val directions = PartyAddFormFragmentDirections
            .actionPartyAddFormFragmentToPartyCardFragment(idParty)
        findNavController().navigate(directions)
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
