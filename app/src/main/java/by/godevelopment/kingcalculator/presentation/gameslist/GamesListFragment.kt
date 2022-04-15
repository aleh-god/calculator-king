package by.godevelopment.kingcalculator.presentation.gameslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class GamesListFragment : Fragment() {

    companion object {
        fun newInstance() = GamesListFragment()
    }

    private lateinit var viewModel: GamesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GamesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}