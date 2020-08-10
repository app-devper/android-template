package com.devper.template.presentation.main

import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.devper.template.AppConfig.SESSION_EXPIRED_ERROR
import com.devper.template.R
import com.devper.template.core.extension.toGone
import com.devper.template.core.extension.toVisible
import com.devper.template.core.platform.fcm.BadgeHelper
import com.devper.template.core.platform.fcm.MessagingHandler
import com.devper.template.core.platform.session.CountDownSession
import com.devper.template.core.platform.widget.ProgressHudDialog
import com.devper.template.data.session.AppSessionProvider
import com.devper.template.databinding.ActivityHomeBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var navController: NavController
    private lateinit var host: NavHostFragment
    private lateinit var messagingHandler: MessagingHandler

    @Inject
    lateinit var session: AppSessionProvider

    @Inject
    lateinit var countDownSession: CountDownSession
    private var isWaitForSecondBackClick = false
    private var currentId: Int = 0
    private val mainViewModel: MainViewModel by viewModels()

    private val progress: ProgressHudDialog by lazy {
        ProgressHudDialog.init(this, "Loading...", false)
    }

    override fun onStart() {
        super.onStart()
        countDownSession.onFinish = {
            mainViewModel.keepAlive()
        }
    }

    override fun setupView() {
        setSupportActionBar(binding.toolbar)
        host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        navController = host.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentId = destination.id
            binding.toolbar.title = destination.label
            applyDisplayHomeAsUpEnabled(hasBack(destination.id))
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            onNavDestinationSelected(it)
        }

        messagingHandler = MessagingHandler(this, lifecycle)
        messagingHandler.subscribeToTopic("all")

        setBadge()
    }

    override fun observeLiveData() {
        messagingHandler.messageLiveData.observe(this, Observer {
            setBadge()
        })

        messagingHandler.tokenLiveData.observe(this, Observer {
            //pref.fbToken = it
        })

        mainViewModel.navigateLiveData.observe(this, Observer {
            navController.navigate(it.first, it.second)
        })

        mainViewModel.profileLiveDate.observe(this, Observer {
            when (it) {
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    mainViewModel.setUser(it.data)
                }
                is ResultState.Error -> {
                    mainViewModel.error(it.throwable)
                }
            }
        })

        mainViewModel.resultLogin.observe(this, Observer {
            when (it) {
                is ResultState.Loading -> {
                }
                is ResultState.Success -> {
                    mainViewModel.setAccessToken(it.data)
                    countDownSession.start()
                }
                is ResultState.Error -> {
                    mainViewModel.error(it.throwable)
                }
            }
        })

        mainViewModel.userLiveData.observe(this, Observer {

        })

        mainViewModel.accessTokenLiveData.observe(this, Observer { accessToken ->
            session.accessToken = accessToken
        })
    }

    private fun setBadge() {
        val badgeCount = BadgeHelper(this).badgeCount.toString()
        mainViewModel.badge.postValue(badgeCount)
    }

    private fun isHome(id: Int): Boolean {
        return id in listOf(R.id.home_dest, R.id.profile_dest, R.id.setting_dest)
    }

    private fun hasBack(id: Int): Boolean {
        return id !in listOf(R.id.pin_code_dest, R.id.home_dest, R.id.profile_dest, R.id.setting_dest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = host.childFragmentManager.primaryNavigationFragment
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.navHostFragment)) || super.onOptionsItemSelected(item)
    }

    private fun onNavDestinationSelected(item: MenuItem): Boolean {
        return navigate(item.itemId)
    }

    private fun navigate(id: Int): Boolean {
        val options = NavOptions.Builder().run {
            setEnterAnim(R.anim.nav_default_enter_anim)
            setExitAnim(R.anim.nav_default_exit_anim)
            setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            build()
        }
        return try {
            navController.navigate(id, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun hideBottomNavigation() {
        binding.bottomNavigation.toGone()
    }

    fun showBottomNavigation() {
        binding.bottomNavigation.toVisible()
    }

    fun clearLogin() {
        mainViewModel.clearAccessToken()
        countDownSession.clear()
        timer?.cancel()
    }

    fun handlerLogin() {
        handlerTimeOut()
        countDownSession.start()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        handlerTimeOut()
        return super.dispatchTouchEvent(ev)
    }

    private fun isLogin(): Boolean {
        return mainViewModel.isLogin()
    }

    private fun handlerTimeOut() {
        timer?.cancel()
        if (isLogin()) {
            timer = object : CountDownTimer(60000L, 1000L) {
                override fun onFinish() {
                    mainViewModel.error(SESSION_EXPIRED_ERROR, getString(R.string.error_idle_time_out))
                    clearLogin()
                }

                override fun onTick(millisUntilFinished: Long) {}
            }.start()
        }
    }

    override fun onBackPressed() {
        when (isHome(currentId)) {
            true -> {
                if (isWaitForSecondBackClick) {
                    finish()
                    return
                }
                isWaitForSecondBackClick = true
                Toast.makeText(this, R.string.back_again_exit, Toast.LENGTH_SHORT).show()
                Handler().postDelayed({ isWaitForSecondBackClick = false }, 2000)
            }
            else -> super.onBackPressed()
        }
    }

    fun showLoading() {
        progress.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    fun hideLoading() {
        progress.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    companion object {
        private var timer: CountDownTimer? = null
    }

}
