package com.devper.template.splash

import android.os.Handler
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devper.template.MainViewModel
import com.devper.template.R
import com.devper.template.appCompat
import com.devper.template.common.AppDatabase
import com.devper.template.common.SPLASH_DELAY
import com.devper.template.common.ui.BaseFragment
import com.devper.template.databinding.FragmentSplashBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, MainViewModel>() {

    private val db: AppDatabase by inject()
    private lateinit var mDelayHandler: Handler

    override fun getLayout() = R.layout.fragment_splash

    override fun initViewModel() = getSharedViewModel<MainViewModel>()

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        mDelayHandler = Handler()
        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY)
    }

    override fun setObserve() {
    }

    private val mRunnable: Runnable = Runnable {
        val navBuilder = NavOptions.Builder()
        val navOptions = navBuilder.setPopUpTo(R.id.splash_dest, true).build()
        val user = db.user().getFirstUser()
        if (user != null) {
            findNavController().navigate(R.id.home_dest, null, navOptions)
        } else {
            findNavController().navigate(R.id.login_dest, null, navOptions)
        }
    }

    override fun onDestroy() {
        mDelayHandler.removeCallbacks(mRunnable)
        super.onDestroy()
    }

}