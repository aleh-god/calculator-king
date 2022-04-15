package by.godevelopment.kingcalculator.presentation.partycard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class PartyCardFragment : Fragment() {

    companion object {
        fun newInstance() = PartyCardFragment()
    }

    private lateinit var viewModel: PartyCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_party_card, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PartyCardViewModel::class.java)
        // TODO: Use the ViewModel
    }

}