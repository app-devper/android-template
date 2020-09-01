package com.devper.template.presentation.home

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.core.platform.widget.CountDrawable
import com.devper.template.databinding.FragmentHomeBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override val viewModel: BaseViewModel by viewModels()

    private var menuItem: MenuItem? = null

    override fun setupView() {
        showToolbar()
        showBottomNavigation()
        setHasOptionsMenu(true)
    }

    override fun onArguments(it: Bundle?) {
    }

    override fun observeLiveData() {
        mainViewModel.badgeLiveData.observe(viewLifecycleOwner, {
            setCount()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.onNavigate(item.itemId, null)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        menuItem = menu.findItem(R.id.notification_dest)
        setCount()
        getUnread()
    }

    private fun setCount() {
        menuItem?.setCount(requireContext(), badge)
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
        icon.invalidateSelf()
    }
}
