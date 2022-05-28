package by.godevelopment.kingcalculator.presentation.partycard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.godevelopment.kingcalculator.databinding.FragmentPartyCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PartyCardFragment : Fragment() {

    companion object {
        fun newInstance() = PartyCardFragment()
    }

    private val viewModel: PartyCardViewModel by viewModels()
    private var _binding: FragmentPartyCardBinding? = null
    private val binding: FragmentPartyCardBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPartyCardBinding.inflate(inflater, container, false)
        val message = "idPlayer = ${viewModel.idPlayer}"
        binding.message.text = message
        return binding.root
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
