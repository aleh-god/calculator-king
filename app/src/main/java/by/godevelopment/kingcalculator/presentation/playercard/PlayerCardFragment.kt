package by.godevelopment.kingcalculator.presentation.playercard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPlayerCardBinding
import by.godevelopment.kingcalculator.presentation.playeraddform.PlayerAddFormViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PlayerCardFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerCardFragment()
    }

    private val viewModel: PlayerCardViewModel by viewModels()
    private var _binding: FragmentPlayerCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerCardBinding.inflate(inflater, container, false)
        setupUi()
        setupListeners()
        setupEvents()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            bttnDelete.setOnClickListener {
                viewModel.onEvent(CardUserEvent.PressDeleteButton)
            }
            bttnSave.setOnClickListener {
                viewModel.onEvent(CardUserEvent.PressSaveButton)
            }
            playerNameEdit.doAfterTextChanged {
                viewModel.onEvent(CardUserEvent.PlayerNameChanged(it.toString()))
            }
        }
    }

    private fun setupUi() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.uiState.collect { uiState ->
                    showProgressUi(uiState.showsProgress)
                    binding.playerNameEdit.text.apply {
                        if(this.isNullOrEmpty()) {
                            binding.playerNameEdit.setText(uiState.playerCardModel.name)
                        }
                    }
                    binding.playerName.error = uiState.playerNameError
                }
            }
        }
    }

    private fun setupEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { event ->
                when(event) {
                    is PlayerCardViewModel.UiEvent.ShowSnackbar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is PlayerCardViewModel.UiEvent.NavigateToList -> {
                        navigateToListUser()
                    }
                }
            }
        }
    }

    private fun navigateToListUser() {
        findNavController().navigate(R.id.action_playerCardFragment_to_playersListFragment)
    }

    private fun showProgressUi(key: Boolean) {
        binding.apply {
            if (key) {
                progress.visibility = View.VISIBLE
                bttnSave.visibility = View.GONE
                bttnDelete.visibility = View.GONE
            } else {
                progress.visibility = View.GONE
                bttnSave.visibility = View.VISIBLE
                bttnDelete.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}