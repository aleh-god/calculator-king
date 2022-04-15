package by.godevelopment.kingcalculator.presentation.partyinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class PartyInfoFragment : Fragment() {

    companion object {
        fun newInstance() = PartyInfoFragment()
    }

    private lateinit var viewModel: PartyInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_party_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PartyInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}