package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvent()
        viewModel.checkUnfinishedGame()
        setupUi()
        setupConfirmDialogListener()
    }

    private fun setupUi() {
        val gamesTableAdapter = PartyCardAdapter(onClick)
        with(binding) {
            gamesTable.adapter = gamesTableAdapter
            gamesTable.layoutManager = LinearLayoutManager(requireContext())
            viewModel.uiState
                .flowWithLifecycle(lifecycle)
                .onEach { uiState ->
                    if (!uiState.isFetchingData) progress.visibility = View.GONE
                    else progress.visibility = View.VISIBLE
                    gamesTableAdapter.items = uiState.dataList
                    playerNameHeader.text = buildString {
                        append(uiState.contractorPlayer)
                        append(getString(R.string.ui_text_contacts))
                    }
                    playerOneName.text = uiState.playersInPartyModel.playerOne
                    playerTwoName.text = uiState.playersInPartyModel.playerTwo
                    playerThreeName.text = uiState.playersInPartyModel.playerThree
                    playerFourName.text = uiState.playersInPartyModel.playerFour
                }
                .launchIn(lifecycle.coroutineScope)
        }
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is PartyCardUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PartyCardFragmentDirections.actionPartyCardFragmentToPartiesListFragment()
                        )
                    }
                    is PartyCardUiEvent.ShowMessage -> {
                        snackbar?.dismiss()
                        snackbar = Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_INDEFINITE)
                            .setAction(event.textAction) { event.onAction() }
                        snackbar?.show()
                    }
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

    override fun onStop() {
        snackbar?.dismiss()
        snackbar = null
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
