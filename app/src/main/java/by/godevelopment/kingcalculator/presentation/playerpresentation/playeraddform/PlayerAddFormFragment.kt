package by.godevelopment.kingcalculator.presentation.playerpresentation.playeraddform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPlayerAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerAddFormFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerAddFormFragment()
    }

    private val viewModel: PlayerAddFormViewModel by viewModels()
    private var _binding: FragmentPlayerAddFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerAddFormBinding.inflate(inflater, container, false)
        setupUi()
        setupListeners()
        setupEvents()
        return binding.root
    }

    private fun setupUi() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.uiState.collect { uiState ->
                    showProgressUi(uiState.showsProgress)

                    playerName.error = if (uiState.playerNameError != null) getString(uiState.playerNameError)
                    else null
                    playerEmail.error = if (uiState.emailError != null) getString(uiState.emailError)
                    else null
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            bttnSave.setOnClickListener {
                viewModel.onEvent(AddFormUserEvent.PressSaveButton)
            }
            playerNameEdit.doAfterTextChanged {
                viewModel.onEvent(AddFormUserEvent.PlayerNameChanged(it.toString()))
            }
            playerEmailEdit.doAfterTextChanged {
                viewModel.onEvent(AddFormUserEvent.EmailChanged(it.toString()))
            }
        }
    }

    private fun setupEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when(event) {
                    is PlayerAddFormViewModel.UiEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is PlayerAddFormViewModel.UiEvent.NavigateToList -> {
                        navigateToListUser()
                    }
                }
            }
        }
    }

    private fun navigateToListUser() {
        findNavController().navigate(R.id.action_playerAddFormFragment_to_playersListFragment)
    }

    private fun showProgressUi(key: Boolean) {
        binding.apply {
            if (key) {
                progress.visibility = View.VISIBLE
                bttnSave.visibility = View.GONE
            } else {
                progress.visibility = View.GONE
                bttnSave.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
