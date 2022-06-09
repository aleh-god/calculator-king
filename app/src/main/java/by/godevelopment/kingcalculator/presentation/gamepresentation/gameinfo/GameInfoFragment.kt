package by.godevelopment.kingcalculator.presentation.gamepresentation.gameinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class GameInfoFragment : Fragment() {

    companion object {
        fun newInstance() = GameInfoFragment()
    }

    private lateinit var viewModel: GameInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}