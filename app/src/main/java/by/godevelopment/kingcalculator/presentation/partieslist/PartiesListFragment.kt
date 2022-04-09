package by.godevelopment.kingcalculator.presentation.partieslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.godevelopment.kingcalculator.R

class PartiesListFragment : Fragment() {

    companion object {
        fun newInstance() = PartiesListFragment()
    }

    private lateinit var viewModel: PartiesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parties_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PartiesListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}