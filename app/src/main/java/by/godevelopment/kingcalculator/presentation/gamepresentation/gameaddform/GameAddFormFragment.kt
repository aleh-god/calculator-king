package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.FragmentGameAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
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
        setupUi()
        setupEvent()
        return binding.root
    }

    private fun setupUi() {
        val multiAdapter = MultiAdapter(
            onClickDec = viewModel::onClickDec,
            onClickInc = viewModel::onClickInc,
            onClickEdit = ::showInputDialog
        )
        binding.apply {
            tricksTable.adapter = multiAdapter
            tricksTable.layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    Log.i(TAG, "GameAddFormFragment setupUi: ${uiState.listMultiItems}")
                    if (!uiState.isFetchingData) {
                        binding.progress.visibility = View.GONE
                    } else binding.progress.visibility = View.VISIBLE

                    binding.headerGameAddForm.text =
                        getString(R.string.fragment_header, uiState.gameTotalScore)
                    multiAdapter.multiList = uiState.listMultiItems
                    binding.buttonSaveResult.setOnClickListener {
                        viewModel.saveGameData()
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
                    { viewModel.reloadDataModel() }
                    .show()
            }
        }
    }

    private fun showInputDialog(rowId: Int) {
        setupInputDialogListener(rowId)
        val dialogFragment = InputValueDialogFragment.newFragmentInstance()
        dialogFragment.show(parentFragmentManager, InputValueDialogFragment.TAG)
    }

    private fun setupInputDialogListener(rowId: Int) {
        parentFragmentManager.setFragmentResultListener(
            InputValueDialogFragment.REQUEST_KEY,
            this,
            FragmentResultListener { _, result ->
                val result = result.getInt(InputValueDialogFragment.KEY_RESPONSE)
                viewModel.onClickEdit(rowId, result)
            }
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
