package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPartyCardBinding
import by.godevelopment.kingcalculator.domain.commons.models.GameType
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PartyCardFragment : Fragment() {

    companion object {
        fun newInstance() = PartyCardFragment()
    }

    private val viewModel: PartyCardViewModel by viewModels()
    private var _binding: FragmentPartyCardBinding? = null
    private val binding: FragmentPartyCardBinding
        get() = _binding!!

    private var gameType: GameType? = null
    private val onClick: (GameType) -> Unit = { key ->
        gameType = key
        showConfirmDialog(key)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyCardBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupEvent(it)
            viewModel.checkUnfinishedGame()
            setupUi(it)
        }
        setupConfirmDialogListener()
        return binding.root
    }

    private fun setupUi(lifecycle: Lifecycle) {
        val gamesTableAdapter = PartyCardAdapter(onClick)
        binding.apply {
            gamesTable.adapter = gamesTableAdapter
            gamesTable.layoutManager = LinearLayoutManager(requireContext())
            lifecycle.coroutineScope.launch {
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .collect { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else progress.visibility = View.VISIBLE
                        gamesTableAdapter.items = uiState.dataList
                        playerNameHeader.text = uiState.contractorPlayer
                        playerOneName.text = uiState.playersInPartyModel.playerOne
                        playerTwoName.text = uiState.playersInPartyModel.playerTwo
                        playerThreeName.text = uiState.playersInPartyModel.playerThree
                        playerFourName.text = uiState.playersInPartyModel.playerFour
                    }
            }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when(event) {
                    is PartyCardUiEvent.ShowMessage -> Snackbar
                        .make(binding.root, event.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.snackbar_btn_reload))
                        { event.onAction.invoke() }
                        .show()
                    is PartyCardUiEvent.NavigateToGameAddForm -> {
                        navigateToGameAddForm(event.navArgs)
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun showConfirmDialog(gameType: GameType) {
        val dialogFragment =
            ConfirmDialogFragment.newFragmentInstance(getString(gameType.res))
        dialogFragment.show(parentFragmentManager, ConfirmDialogFragment.TAG)
    }

    private fun setupConfirmDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            ConfirmDialogFragment.REQUEST_KEY,
            this
        ) { _, result ->
            when (result.getInt(ConfirmDialogFragment.KEY_RESPONSE)) {
                DialogInterface.BUTTON_POSITIVE -> {
                    confirmAction()
                }
                DialogInterface.BUTTON_NEGATIVE -> {}
            }
        }
    }

    private fun confirmAction() {
        gameType?.let { type -> viewModel.createGameNote(type) }
    }

    private fun navigateToGameAddForm(navArgs: Long) {
        val action =
            PartyCardFragmentDirections.actionPartyCardFragmentToGameAddFormFragment(navArgs)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
