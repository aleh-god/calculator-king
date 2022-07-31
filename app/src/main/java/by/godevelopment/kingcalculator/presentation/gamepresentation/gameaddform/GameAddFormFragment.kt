package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

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
import by.godevelopment.kingcalculator.databinding.FragmentGameAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameAddFormFragment : Fragment() {

    companion object {
        fun newInstance() = GameAddFormFragment()
    }

    private val viewModel: GameAddFormViewModel by viewModels()
    private var _binding: FragmentGameAddFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameAddFormBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupUi(it)
            setupEvent(it)
        }
        return binding.root
    }

    private fun setupUi(lifecycle: Lifecycle) {
        val multiAdapter = MultiAdapter(
            onClickDec = viewModel::onClickDec,
            onClickInc = viewModel::onClickInc,
            onClickEdit = ::showInputDialog
        )
        binding.apply {
            tricksTable.adapter = multiAdapter
            tricksTable.layoutManager = LinearLayoutManager(requireContext())
            lifecycle.coroutineScope.launch {
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .collect { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else binding.progress.visibility = View.VISIBLE
                        headerGameAddForm.text =
                            getString(R.string.fragment_header, uiState.gameTotalScore)
                        multiAdapter.multiList = uiState.listMultiItems
                        buttonSaveResult.setOnClickListener { viewModel.saveGameData() }
                    }
            }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is GameAddFormUiEvent.ShowMessageUiEvent -> {
                        Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.snackbar_btn_neutral_ok))
                            { event.onAction.invoke() }
                            .show()
                    }
                    is GameAddFormUiEvent.NavigateToPartyCardUiEvent -> navigateToPartyCard()
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun showInputDialog(rowId: Int) {
        setupInputDialogListener(rowId)
        val dialogFragment = InputValueDialogFragment.newFragmentInstance()
        dialogFragment.show(parentFragmentManager, InputValueDialogFragment.TAG)
    }

    private fun setupInputDialogListener(rowId: Int) {
        parentFragmentManager.setFragmentResultListener(
            InputValueDialogFragment.REQUEST_KEY,
            this
        ) { _, bundle ->
            val result = bundle.getInt(InputValueDialogFragment.KEY_RESPONSE)
            viewModel.onClickEdit(rowId, result)
        }
    }

    private fun navigateToPartyCard() {
        findNavController().navigate(R.id.action_gameAddFormFragment_to_partyCardFragment)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
