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
    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupUi()
        setupEvent()
    }

    private fun setupListeners() {
        binding.buttonFloatingAction.setOnClickListener {
            findNavController().navigate(R.id.action_playersListFragment_to_playerAddFormFragment)
        }
    }

    private fun setupUi() {
        val rvAdapter = PlayersAdapter(
            onClickItem = ::navigateToPlayerCard,
            onClickImage = ::navigateToPlayerInfo
        )
        with(binding) {
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireContext())
                viewModel.uiState
                    .flowWithLifecycle(lifecycle)
                    .onEach { uiState ->
                        if (!uiState.isFetchingData) progress.visibility = View.GONE
                        else progress.visibility = View.VISIBLE
                        rvAdapter.itemList = uiState.dataList
                    }
                    .launchIn(lifecycle.coroutineScope)
        }
    }

    private fun setupEvent() {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                when (event) {
                    is PlayersListUiEvent.NavigateToBackScreen -> {
                        findNavController().navigate(
                            PlayersListFragmentDirections
                                .actionPlayersListFragmentToPartiesListFragment()
                        )
                    }
                    is PlayersListUiEvent.ShowMessage -> {
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

    private fun navigateToPlayerCard(playerId: Long) {
        val direction = PlayersListFragmentDirections
            .actionPlayersListFragmentToPlayerCardFragment(playerId)
        findNavController().navigate(direction)
    }

    private fun navigateToPlayerInfo(playerId: Long) {
        val direction = PlayersListFragmentDirections
            .actionPlayersListFragmentToPlayerInfoFragment(playerId)
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
