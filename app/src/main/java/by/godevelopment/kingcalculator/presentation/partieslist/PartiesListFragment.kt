package by.godevelopment.kingcalculator.presentation.partieslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.FragmentPartiesListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PartiesListFragment : Fragment() {

    companion object {
        fun newInstance() = PartiesListFragment()
    }

    private val viewModel: PartiesListViewModel by viewModels()
    private var _binding: FragmentPartiesListBinding? = null
    private val binding get() = _binding!!

    private val onClick: (Int) -> Unit = { key ->
        Log.i(TAG, "PartiesListFragment: onClick $key")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartiesListBinding.inflate(inflater, container, false)
        setupUi()
        setupEvent()
        return binding.root
    }

    private fun setupUi() {
        val rvAdapter = PartiesAdapter(onClick)
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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}