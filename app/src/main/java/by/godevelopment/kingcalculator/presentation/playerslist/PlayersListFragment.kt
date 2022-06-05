package by.godevelopment.kingcalculator.presentation.playerslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.FragmentPlayersListBinding
import by.godevelopment.kingcalculator.presentation.playeraddform.PlayerAddFormFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PlayersListFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersListFragment()
    }

    private val viewModel: PlayersListViewModel by viewModels()
    private var _binding: FragmentPlayersListBinding? = null
    private val binding get() = _binding!!

    private val onClick: (Long) -> Unit = { key ->
        Log.i(TAG, "PlayersListFragment: onClick $key")
        navigateToPlayerCard(key)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersListBinding.inflate(inflater, container, false)
        setupUi()
        setupListeners()
        setupEvent()
        return binding.root
    }

    private fun setupListeners() {
        binding.buttonFloatingAction.setOnClickListener {
            findNavController().navigate(R.id.action_playersListFragment_to_playerAddFormFragment)
        }
    }

    private fun setupUi() {
        val rvAdapter = PlayersAdapter(onClick)
        binding.apply {
            rv.adapter = rvAdapter
            rv.layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { uiState ->
                Log.i(TAG, "setupUi: $uiState")
                if (!uiState.isFetchingData) {
                    binding.progress.visibility = View.GONE
                } else binding.progress.visibility = View.VISIBLE
                rvAdapter.itemList = uiState.dataList
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