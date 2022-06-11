package by.godevelopment.kingcalculator.presentation.gamepresentation.gameaddform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.godevelopment.kingcalculator.databinding.FragmentGameAddFormBinding

class GameAddFormFragment : Fragment() {

    companion object {
        fun newInstance() = GameAddFormFragment()
    }

    private val viewModel: GameAddFormViewModel by viewModels()
    private var _binding: FragmentGameAddFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameAddFormBinding.inflate(inflater, container, false)

        return binding.root
    }
}
