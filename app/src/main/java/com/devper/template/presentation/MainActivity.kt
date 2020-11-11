package com.devper.template.presentation

import android.content.Intent
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.devper.template.AppConfig.SESSION_EXPIRED_ERROR
import com.devper.template.R
import com.devper.template.core.exception.AppException
import com.devper.template.core.extension.toGone
import com.devper.template.core.extension.toVisible
import com.devper.template.core.platform.fcm.BadgeHelper
import com.devper.template.core.platform.fcm.MessagingHandler
import com.devper.template.core.platform.session.CountDownSession
import com.devper.template.core.platform.widget.ProgressHudDialog
import com.devper.template.databinding.ActivityHomeBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private lateinit var navController: NavController
    private lateinit var host: NavHostFragment
    private lateinit var messagingHandler: MessagingHandler

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
            applyDisplayHomeAsUpEnabled(haveBack(currentId))
            if (isHome(currentId)) {
                showBottomNavigation()
            } else {
                hideBottomNavigation()
            }
            if (haveToolbar(currentId)) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            onNavDestinationSelected(it)
        }

        messagingHandler = MessagingHandler(this, lifecycle)
        messagingHandler.apply {
            subscribeToTopic("all")
            onMessage = {
                mainViewModel.setMessage(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getLogin()
    }

    override fun observeLiveData() {
        mainViewModel.profileLiveData.observe(this, {
            when (it) {
                is ResultState.Error -> {
                    toError(it.throwable.toError())
                }
            }
        })

        mainViewModel.isLoginData.observe(this, {
            if (mainViewModel.isLogin()) {
                mainViewModel.getProfile()
                mainViewModel.subscription()
                mainViewModel.getUnread()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })

        mainViewModel.badgeLiveData.observe(this, {
            val badgeCount = BadgeHelper(this)
            badgeCount.badgeCount = it
            when {
                it <= 0 -> removeBadge(binding.bottomNavigation, R.id.notification_dest)
                else -> showBadge(binding.bottomNavigation, R.id.notification_dest, it)
            }
        })
    }

    private fun isHome(id: Int): Boolean {
        return id in listOf(
            R.id.home_dest,
            R.id.profile_dest,
            R.id.setting_dest,
            R.id.notification_dest,
        )
    }

    private fun haveBack(id: Int): Boolean {
        return id !in listOf(
            R.id.login_pin_dest,
            R.id.home_dest,
            R.id.profile_dest,
            R.id.setting_dest,
            R.id.notification_dest,
            R.id.pin_max_attempt_dest,
            R.id.verify_pin_dest,
            R.id.suspend_account_dest,
            R.id.set_pin_dest,
        )
    }

    private fun haveToolbar(id: Int): Boolean {
        return id !in listOf(
            R.id.splash_dest,
            R.id.login_dest,
            R.id.pin_max_attempt_dest,
            R.id.suspend_account_dest
        )
    }

    private fun canBack(id: Int): Boolean {
        return id !in listOf(
            R.id.pin_max_attempt_dest,
            R.id.verify_pin_dest,
            R.id.suspend_account_dest,
            R.id.set_pin_dest,
        )
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

    private fun hideBottomNavigation() {
        binding.bottomNavigation.toGone()
    }

    private fun showBottomNavigation() {
        binding.bottomNavigation.toVisible()
    }

    fun clearLogin() {
        mainViewModel.clearUser()
        countDownSession.clear()
        timer?.cancel()
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
            timer = object : CountDownTimer(300000L, 1000L) {
                override fun onFinish() {
                    sessionExpired()
                }

                override fun onTick(millisUntilFinished: Long) {}
            }.start()
        }
    }

    fun sessionExpired() {
        mainViewModel.error(SESSION_EXPIRED_ERROR, getString(R.string.error_idle_time_out))
        clearLogin()
    }

    fun toError(throwable: AppException) {
        mainViewModel.error(throwable.resultCode, throwable.getDesc())
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
                lifecycleScope.launch {
                    delay(2000)
                    isWaitForSecondBackClick = false
                }
            }
            else -> {
                if (canBack(currentId)) {
                    super.onBackPressed()
                }
            }
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

    private fun showBadge(bottomNavigationView: BottomNavigationView, id: Int, value: Int) {
        val badgeView = bottomNavigationView.getOrCreateBadge(id)
        badgeView.number = value
        badgeView.maxCharacterCount = 3
    }

    private fun removeBadge(bottomNavigationView: BottomNavigationView, id: Int) {
        bottomNavigationView.removeBadge(id)
    }

    companion object {
        private var timer: CountDownTimer? = null
    }

}
