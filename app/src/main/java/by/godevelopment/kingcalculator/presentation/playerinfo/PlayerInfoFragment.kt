package by.godevelopment.kingcalculator.presentation.playerinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class PlayerInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerInfoFragment()
    }

    private lateinit var viewModel: PlayerInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayerInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}