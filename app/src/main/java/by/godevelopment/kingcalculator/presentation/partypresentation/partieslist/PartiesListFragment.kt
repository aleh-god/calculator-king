package by.godevelopment.kingcalculator.presentation.partypresentation.partieslist

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
import by.godevelopment.kingcalculator.databinding.FragmentPartiesListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PartiesListFragment : Fragment() {

    companion object {
        fun newInstance() = PartiesListFragment()
    }

    private val viewModel: PartiesListViewModel by viewModels()
    private var _binding: FragmentPartiesListBinding? = null
    private val binding get() = _binding!!

    private val onClick: (Long) -> Unit = { key ->
        navigateToPartyCard(key)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartiesListBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupUi(it)
            setupEvent(it)
        }
        setupListeners()
        return binding.root
    }

    private fun setupUi(lifecycle: Lifecycle) {
        val rvAdapter = PartiesAdapter(onClick)
        binding.apply {
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireContext())
            lifecycle.coroutineScope.launch {
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .collect { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else progress.visibility = View.VISIBLE
                        rvAdapter.items = uiState.dataList
                    }
            }
        }
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                Snackbar
                    .make(binding.root, event, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.snackbar_btn_reload))
                    { viewModel.onAction() }
                    .show()
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupListeners() {
        binding.floatingActionButton.setOnClickListener {
            // TODO("validate min players count")
            findNavController().navigate(R.id.action_partiesListFragment_to_partyAddFormFragment)
        }
    }

    private fun navigateToPartyCard(idParty: Long) {
        val direction = PartiesListFragmentDirections
            .actionPartiesListFragmentToPartyCardFragment(idParty)
        findNavController().navigate(direction)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}