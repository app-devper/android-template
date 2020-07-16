package com.devper.template.presentation.home

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.core.platform.widget.CountDrawable
import com.devper.template.databinding.FragmentHomeBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel: BaseViewModel by viewModel()

    private var menuItem: MenuItem? = null

    override fun setupView() {
        showToolbar()
        showBottomNavigation()
        setHasOptionsMenu(true)
        binding.viewModel = mainViewModel
    }

    override fun onArguments(it: Bundle?) {
        mainViewModel.initProfile()
    }

    override fun observeLiveData() {
        mainViewModel.badge.observe(viewLifecycleOwner, Observer {
            menuItem?.setCount(requireContext(), it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        menuItem = menu.findItem(R.id.home_dest)
        val badge = mainViewModel.badge.value
        menuItem?.setCount(requireContext(), badge ?: "0")
    }

    private fun MenuItem.setCount(context: Context, count: String) {
        val icon = this.icon as LayerDrawable
        val badge: CountDrawable
        // Reuse drawable if possible
        val reuse = icon.findDrawableByLayerId(R.id.ic_group_count)
        badge = if (reuse != null && reuse is CountDrawable) {
            reuse
        } else {
            CountDrawable(context)
        }
        badge.setCount(count)
        icon.mutate()
        icon.setDrawableByLayerId(R.id.ic_group_count, badge)
    }
}
