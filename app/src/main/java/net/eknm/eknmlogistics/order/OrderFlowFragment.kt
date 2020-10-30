package net.eknm.eknmlogistics.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import net.eknm.eknmlogistics.R
import net.eknm.eknmlogistics.android.base.navigation.BaseFlowFragment
import net.eknm.eknmlogistics.order.destinationFragment.DestinationFragment
import net.eknm.eknmlogistics.order.destinationFragment.DestinationViewModel

class OrderFlowFragment : BaseFlowFragment<OrderFlowViewModel>() {
    override val vmClass = OrderFlowViewModel::class.java
    override fun initFlow() {
        showFragment(DestinationFragment.newInstance(getString(R.string.select_start_destination)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        when (childFragment) {
            is DestinationFragment -> observeDestinationsViewModel(childFragment.viewModel)
        }
    }

    private fun observeViewModel() {
        viewModel.showEndDestinationFragmentEvent.observe(viewLifecycleOwner, Observer {
            showFragment(DestinationFragment.newInstance(getString(R.string.select_end_destination)))
        })
    }

    private fun observeDestinationsViewModel(destinationViewModel: DestinationViewModel) {
        destinationViewModel.destinationSelected.observe(this, Observer {
            viewModel.onNewLocation(it)
        })
    }

    companion object {
        fun newInstance() = OrderFlowFragment()
    }
}