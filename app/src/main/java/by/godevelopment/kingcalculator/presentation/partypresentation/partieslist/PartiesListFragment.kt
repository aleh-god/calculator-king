package by.godevelopment.kingcalculator.presentation.partypresentation.partieslist

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
import by.godevelopment.kingcalculator.databinding.FragmentPartiesListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PartiesListFragment : Fragment() {

    companion object {
        fun newInstance() = PartiesListFragment()
    }

    private val viewModel: PartiesListViewModel by viewModels()
    private var _binding: FragmentPartiesListBinding? = null
    private val binding get() = _binding!!
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartiesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupUi()
        setupEvent()
    }

    private fun setupUi() {
        val rvAdapter = PartiesAdapter(
            onItemClick = ::checkPlayersIsActiveAndNavigateToPartyCard,
            onStatClick = ::navigateToPartyInfo,
            onDelClick = ::deleteParty
        )
        with(binding) {
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireContext())

            viewModel.uiState
                .flowWithLifecycle(lifecycle)
                .onEach { uiState ->
                    if (!uiState.isFetchingData) progress.visibility = View.GONE
                    else progress.visibility = View.VISIBLE
                    rvAdapter.items = uiState.dataList
                }
                .launchIn(lifecycle.coroutineScope)
        }
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is PartiesListUiEvent.NavigateToPartyAddForm -> navigateToPartyAddForm()
                    is PartiesListUiEvent.NavigateToPartyCard -> navigateToPartyCard(event.navArgs)
                    is PartiesListUiEvent.NavigateToPartyInfo -> navigateToPartyInfo(event.navArgs)
                    is PartiesListUiEvent.ShowMessage -> {
                        snackbar?.dismiss()
                        snackbar = Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_LONG)
                            .setAction(event.textAction) { event.onAction() }
                        snackbar?.show()
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun setupListeners() {
        binding.floatingActionButton.setOnClickListener {
            viewModel.checkPlayersMinAndNavigate()
        }
    }

    private fun deleteParty(partyId: Long) {
        viewModel.deleteParty(partyId)
    }

    private fun checkPlayersIsActiveAndNavigateToPartyCard(partyId: Long) {
        viewModel.checkPlayersIsActiveAndNavigateToPartyCard(partyId)
    }

    private fun navigateToPartyAddForm() {
        findNavController().navigate(R.id.action_partiesListFragment_to_partyAddFormFragment)
    }

    private fun navigateToPartyCard(partyId: Long) {
        val direction = PartiesListFragmentDirections
            .actionPartiesListFragmentToPartyCardFragment(partyId)
        findNavController().navigate(direction)
    }

    private fun navigateToPartyInfo(partyId: Long) {
        val direction = PartiesListFragmentDirections
            .actionPartiesListFragmentToPartyInfoFragment(partyId)
        findNavController().navigate(direction)
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
