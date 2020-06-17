package com.devper.template.presentation.splash

import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devper.template.R
import com.devper.template.databinding.FragmentSplashBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.appCompat
import com.devper.template.presentation.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        mainViewModel.registerDevice()
    }

    override fun setObserve() {
        mainViewModel.user.observe(this, Observer {
            val navBuilder = NavOptions.Builder()
            val navOptions = navBuilder.setPopUpTo(R.id.splash_dest, true).build()
            if (it != null) {
                findNavController().navigate(R.id.home_dest, null, navOptions)
            } else {
                findNavController().navigate(R.id.login_dest, null, navOptions)
            }
        })
    }

}