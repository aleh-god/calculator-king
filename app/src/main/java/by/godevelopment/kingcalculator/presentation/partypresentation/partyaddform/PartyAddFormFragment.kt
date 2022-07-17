package by.godevelopment.kingcalculator.presentation.partypresentation.partyaddform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPartyAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PartyAddFormFragment : Fragment() {

    companion object {
        fun newInstance() = PartyAddFormFragment()
    }

    private val viewModel: PartyAddFormViewModel by viewModels()
    private var _binding: FragmentPartyAddFormBinding? = null
    private val binding: FragmentPartyAddFormBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyAddFormBinding.inflate(inflater, container, false)
        setupUi()
        setupListeners()
        setupEvents()
        return binding.root
    }

    private fun setupUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                showProgressUi(uiState.showsProgress)
                binding.apply {
                    partyName.error = uiState.partyNameError

                    playerOneMenu.error = uiState.playerOneError
                    playerTwoMenu.error = uiState.playerTwoError
                    playerThreeMenu.error = uiState.playerThreeError
                    playerFourMenu.error = uiState.playerFourError

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

    private fun setupEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when(event) {
                    is PartyAddFormViewModel.UiEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is PartyAddFormViewModel.UiEvent.NavigateToList -> {
                        navigateToPartyCard(event.idParty)
                    }
                }
            }
        }
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
