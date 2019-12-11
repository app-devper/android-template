package com.devper.template.presentation.home

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.core.widget.CountDrawable
import com.devper.template.databinding.FragmentHomeBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.MainViewModel
import com.devper.template.presentation.main.appCompat
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private var menuItem: MenuItem? = null

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun setupView() {
        setHasOptionsMenu(true)
        binding.viewModel = mainViewModel
        appCompat().supportActionBar?.show()
    }

    override fun setObserve() {
        with(mainViewModel) {
            badge.observe(viewLifecycleOwner, Observer {
                Log.i("Badge", "Badge: $it")
                menuItem?.setCount(requireContext(), it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        menuItem = menu.findItem(R.id.member_dest)
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
