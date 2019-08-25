package com.devper.template.home

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.devper.template.MainViewModel
import com.devper.template.R
import com.devper.template.app.ext.setCount
import com.devper.template.appCompat
import com.devper.template.app.ui.BaseFragment
import com.devper.template.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {

    private var menuItem: MenuItem? = null

    override fun getLayout(): Int {
        setHasOptionsMenu(true)
        return R.layout.fragment_home
    }

    override fun initViewModel() = getSharedViewModel<MainViewModel>()

    override fun setupView() {
        binding.viewModel = viewModel
        appCompat().supportActionBar?.show()

    }

    override fun setObserve() {
        with(viewModel) {
            badge.observe(viewLifecycleOwner, Observer {
                Log.i("Badge", "Badge: $it")
                menuItem?.setCount(requireContext(), it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        menuItem = menu.findItem(R.id.member_dest)
        val badge = viewModel.badge.value
        menuItem?.setCount(requireContext(), badge ?: "0")
    }
}
