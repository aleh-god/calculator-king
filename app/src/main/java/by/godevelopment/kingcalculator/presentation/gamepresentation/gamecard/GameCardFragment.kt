package by.godevelopment.kingcalculator.presentation.gamepresentation.gamecard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class GameCardFragment : Fragment() {

    companion object {
        fun newInstance() = GameCardFragment()
    }

    private lateinit var viewModel: GameCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_card, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameCardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}