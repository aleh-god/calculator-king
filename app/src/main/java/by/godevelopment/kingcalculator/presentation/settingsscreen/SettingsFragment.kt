package by.godevelopment.kingcalculator.presentation.settingsscreen

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import by.godevelopment.kingcalculator.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private val viewModel: SettingsViewModel by viewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!
    private var onActionForDialog: (()-> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        setupListeners()
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
                        if (!uiState.isProgress) progress.visibility = View.GONE
                        else progress.visibility = View.VISIBLE
                    }
            }
        }
    }

    private fun setupListeners() {
        setupConfirmDialogListener()
        binding.bttnDeleteParties.setOnClickListener {
            onActionForDialog = { viewModel.deleteAllParties() }
            showDeleteConfirmDialog()
        }
        binding.bttnDeleteGames.setOnClickListener {
            onActionForDialog = { viewModel.deleteAllGames() }
            showDeleteConfirmDialog()
        }
        binding.bttnDeleteAll.setOnClickListener {
            onActionForDialog = { viewModel.deleteAll() }
            showDeleteConfirmDialog()
        }
    }

    private fun setupConfirmDialogListener() {
        parentFragmentManager.setFragmentResultListener(
            DeleteConfirmDialogFragment.REQUEST_KEY,
            this,
            FragmentResultListener { _, result ->
                when(result.getInt(DeleteConfirmDialogFragment.KEY_RESPONSE)) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        onActionForDialog?.invoke()
                        onActionForDialog = null
                    }
                    DialogInterface.BUTTON_NEGATIVE -> { onActionForDialog = null }
                }
            }
        )
    }

    private fun showDeleteConfirmDialog() {
        val dialogFragment = DeleteConfirmDialogFragment()
        dialogFragment.show(parentFragmentManager, DeleteConfirmDialogFragment.TAG)
    }

    private fun setupEvent(lifecycle: Lifecycle) {
        viewModel.uiEvent
            .flowWithLifecycle(lifecycle)
            .onEach { event ->
                // TODO("impl Snackbar scope")
                when(event){
                    is SettingsUiEvent.NavigateToSystemSettings -> {
                        // ("Impl to next time")
                    }
                    is SettingsUiEvent.ShowMessage -> {
                        if (event.valueForText != null) {
                            Snackbar
                                .make(
                                    binding.root,
                                    getString(event.message, event.valueForText),
                                    Snackbar.LENGTH_LONG)
                                .setAction(event.textAction)
                                { event.onAction() }
                                .show()
                        }
                        else {
                            Snackbar
                                .make(binding.root, event.message, Snackbar.LENGTH_LONG)
                                .setAction(event.textAction)
                                { event.onAction() }
                                .show()
                        }
                    }
                }
            }
            .launchIn(lifecycle.coroutineScope)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
