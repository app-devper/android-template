package com.devper.template.home

import android.view.Menu
import android.view.MenuInflater
import com.devper.template.R
import com.devper.template.appCompat
import com.devper.template.common.ui.BaseFragment
import com.devper.template.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getLayout(): Int {
        setHasOptionsMenu(true)
        return R.layout.fragment_home
    }

    override fun initViewModel() = getViewModel<HomeViewModel>()

    override fun setupView() {
        binding.viewModel = viewModel
        appCompat().supportActionBar?.show()

    }

    override fun setObserve() {
        with(viewModel) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

}
