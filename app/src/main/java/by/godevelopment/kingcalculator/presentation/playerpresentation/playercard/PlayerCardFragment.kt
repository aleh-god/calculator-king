package by.godevelopment.kingcalculator.presentation.playerpresentation.playercard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import by.godevelopment.kingcalculator.databinding.FragmentPlayerCardBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PlayerCardFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerCardFragment()
    }

    private val viewModel: PlayerCardViewModel by viewModels()
    private var _binding: FragmentPlayerCardBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerCardBinding.inflate(inflater, container, false)
        setupUi()
        setupEvent()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() = with(binding) {
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

    private fun setupUi() = with(binding) {
        viewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { uiState ->
                showProgress(uiState.showsProgress)
                playerNameEdit.text.apply {
                    if (this.isNullOrEmpty()) playerNameEdit.setText(uiState.playerModel.name)
                }
                playerName.error = uiState.playerNameError?.let { getString(it) }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is PlayerCardUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PlayerCardFragmentDirections
                                .actionPlayerCardFragmentToPlayersListFragment()
                        )
                    }
                    is PlayerCardUiEvent.ShowMessage -> {
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

    private fun showProgress(key: Boolean) = with(binding) {
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
