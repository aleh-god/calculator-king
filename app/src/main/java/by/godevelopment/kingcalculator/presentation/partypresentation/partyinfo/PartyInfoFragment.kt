package by.godevelopment.kingcalculator.presentation.partypresentation.partyinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.databinding.FragmentPartyInfoBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PartyInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PartyInfoFragment()
    }

    private val viewModel: PartyInfoViewModel by viewModels()
    private var _binding: FragmentPartyInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyInfoBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupUi(it)
            setupEvent(it)
        }
        return binding.root
    }

    private fun setupUi(lifecycle: Lifecycle) {
        binding.apply {
            lifecycle.coroutineScope.launch {
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .collect { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else binding.progress.visibility = View.VISIBLE
                        message.text = "partyId = ${viewModel.partyId}"
                    }
            }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    else -> Snackbar
                        .make(binding.root, event, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.snackbar_btn_neutral_ok))
                        { viewModel.reload() }
                        .show()
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}