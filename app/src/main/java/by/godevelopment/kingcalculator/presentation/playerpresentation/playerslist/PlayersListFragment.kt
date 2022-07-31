package by.godevelopment.kingcalculator.presentation.playerpresentation.playerslist

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
import by.godevelopment.kingcalculator.databinding.FragmentPlayersListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersListFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersListFragment()
    }

    private val viewModel: PlayersListViewModel by viewModels()
    private var _binding: FragmentPlayersListBinding? = null
    private val binding get() = _binding!!

    private val onClick: (Long) -> Unit = { key -> navigateToPlayerCard(key) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersListBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycle.also {
            setupUi(it)
            setupEvent(it)
        }
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.buttonFloatingAction.setOnClickListener {
            findNavController().navigate(R.id.action_playersListFragment_to_playerAddFormFragment)
        }
    }

    private fun setupUi(lifecycle: Lifecycle) {
        val rvAdapter = PlayersAdapter(onClick)
        binding.apply {
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireContext())
            lifecycle.coroutineScope.launch {
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .collect { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else progress.visibility = View.VISIBLE
                        rvAdapter.itemList = uiState.dataList
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
                    { viewModel.fetchDataModel() }
                    .show()
            }
            .launchIn(lifecycle.coroutineScope)
    }

    private fun navigateToPlayerCard(idPlayer: Long) {
        val direction = PlayersListFragmentDirections
            .actionPlayersListFragmentToPlayerCardFragment(idPlayer)
        findNavController().navigate(direction)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}