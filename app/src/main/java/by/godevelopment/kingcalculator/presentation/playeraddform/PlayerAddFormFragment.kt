package by.godevelopment.kingcalculator.presentation.playeraddform

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.FragmentPlayerAddFormBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PlayerAddFormFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerAddFormFragment()
    }

    private val viewModel: PlayerAddFormViewModel by viewModels()
    private var _binding: FragmentPlayerAddFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerAddFormBinding.inflate(inflater, container, false)
        setupUI()
        setupListeners()
        setupEvent()
        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.uiState.collect { uiState ->
                    Log.i(TAG, "setupUI: $uiState")
                    if (uiState.isSavingData) {
                        progress.visibility = View.VISIBLE
                    } else {
                        progress.visibility = View.GONE
                    }
                    if (uiState.nameIsValid && uiState.emailIsValid) {
                        bttnSave.visibility = View.VISIBLE
                        bttnSave.isClickable = true
                    } else {
                        bttnSave.visibility = View.GONE
                        bttnSave.isClickable = false
                    }
                    if (playerNameEdit.text.isNullOrBlank() || uiState.nameIsValid)
                        playerName.error = null
                        else playerName.error = getString(R.string.error_name_no_valid)
                    if (playerEmailEdit.text.isNullOrBlank() || uiState.emailIsValid)
                        playerEmail.error = null
                        else playerEmail.error = getString(R.string.error_email_no_valid)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.apply {
            playerNameEdit.doAfterTextChanged {
                viewModel.checkNameFields(it)
            }
            playerEmailEdit.doAfterTextChanged {
                viewModel.checkEmailFields(it)
            }
            bttnSave.setOnClickListener {
                    viewModel.savePlayerDataToRepository(
                        playerNameEdit.text,
                        playerEmailEdit.text
                    )
            }
        }
    }

    private fun setupEvent() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect {
                Snackbar
                    .make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.ok))
                    {
                        Log.i(TAG, "Snackbar: Action")
                    }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}