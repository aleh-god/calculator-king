package by.godevelopment.kingcalculator.presentation.partypresentation.partycard

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.FragmentPartyCardBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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

    private var gameTypeName: String? = null
    private val onClick: (String) -> Unit = { key ->
        gameTypeName = key
        showConfirmDialog(key)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyCardBinding.inflate(inflater, container, false)

        setupUi()
        setupEvent()
        setupConfirmDialogListener()
        return binding.root
    }

    private fun setupUi() {
        val gamesTableAdapter = PartyCardAdapter(onClick)
        binding.apply {
            gamesTable.adapter = gamesTableAdapter
            // gamesTable.layoutManager = GridLayoutManager(requireContext(), 4)
            gamesTable.layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    Log.i(TAG, "setupUi: $uiState")
                    if (!uiState.isFetchingData) { binding.progress.visibility = View.GONE }
                    else binding.progress.visibility = View.VISIBLE
                    gamesTableAdapter.items = uiState.dataList
                    binding.apply {
                        playerNameHeader.text = uiState.contractorPlayer
                        playerOneName.text = uiState.playersInPartyModel.playerOne
                        playerTwoName.text = uiState.playersInPartyModel.playerTwo
                        playerThreeName.text = uiState.playersInPartyModel.playerThree
                        playerFourName.text = uiState.playersInPartyModel.playerFour
                    }
                }
            }
        }
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect {
                Snackbar
                    .make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.snackbar_btn_reload))
                    { viewModel.fetchDataModel() }
                    .show()
            }
        }
    }

    private fun showConfirmDialog(gameTypeName: String) {
        val dialogFragment = ConfirmDialogFragment.newFragmentInstance(gameTypeName)
        dialogFragment.show(parentFragmentManager, ConfirmDialogFragment.TAG)
    }

    private fun setupConfirmDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            ConfirmDialogFragment.REQUEST_KEY,
            this,
            FragmentResultListener { _, result ->
            when(result.getInt(ConfirmDialogFragment.KEY_RESPONSE)) {
                DialogInterface.BUTTON_POSITIVE -> { navigateToGameAddForm() }
                DialogInterface.BUTTON_NEGATIVE -> {}
            }
        }
        )
    }

    private fun navigateToGameAddForm() {
        Log.i(TAG, "PartyCardFragment: onClick $gameTypeName")
        gameTypeName?.let {
            val direction = PartyCardFragmentDirections.actionPartyCardFragmentToGameAddFormFragment(5)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
