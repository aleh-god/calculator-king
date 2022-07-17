package by.godevelopment.kingcalculator.presentation.playerpresentation.playerinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.godevelopment.kingcalculator.databinding.FragmentPlayerInfoBinding

class PlayerInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerInfoFragment()
    }

    private val viewModel: PlayerInfoViewModel by viewModels()
    private var _binding: FragmentPlayerInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
        setupUi()
        return binding.root
    }

    private fun setupUi() {
        val helloMessage = "Player: ${viewModel.idPlayer}"
        binding.message.text = helloMessage
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
