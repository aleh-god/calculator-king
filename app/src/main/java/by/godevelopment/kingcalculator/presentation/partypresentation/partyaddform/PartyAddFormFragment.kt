package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPartyAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyAddFormBinding.inflate(inflater, container, false)
        setupUi()
        setupEvent()
        setupListeners()
        return binding.root
    }

    private fun setupUi() = with(binding) {
        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                showProgressUi(uiState.showsProgress)

                partyName.error = uiState.partyNameError?.let { getString(it) }
                playerOneMenu.error = uiState.playerOneError?.let { getString(it) }
                playerTwoMenu.error = uiState.playerTwoError?.let { getString(it) }
                playerThreeMenu.error = uiState.playerThreeError?.let { getString(it) }
                playerFourMenu.error = uiState.playerFourError?.let { getString(it) }

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
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
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
                            .setAction(event.textAction) { event.onAction() }
                        snackbar?.show()
                    }
                    is PartyAddFormUiEvent.NavigateToList -> {
                        navigateToPartyCard(event.idParty)
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupListeners() = with(binding) {
        bttnStart.setOnClickListener {
            viewModel.onEvent(AddPartyFormUserEvent.PressStartButton)
        }
        partyNameEdit.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PartyNameChanged(it.toString()))
        }
        playerOneInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerOneNameChanged(it.toString()))
        }
        playerTwoInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerTwoNameChanged(it.toString()))
        }
        playerThreeInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerThreeNameChanged(it.toString()))
        }
        playerFourInputMenu.doAfterTextChanged {
            viewModel.onEvent(AddPartyFormUserEvent.PlayerFourNameChanged(it.toString()))
        }
    }

    private fun showProgressUi(key: Boolean) = with(binding) {
        if (key) {
            progress.visibility = View.VISIBLE
            bttnStart.visibility = View.GONE
        } else {
            progress.visibility = View.GONE
            bttnStart.visibility = View.VISIBLE
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
