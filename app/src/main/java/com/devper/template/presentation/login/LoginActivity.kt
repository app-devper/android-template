package com.devper.template.presentation.login

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.devper.template.R
import com.devper.template.core.platform.widget.ProgressHudDialog
import com.devper.template.databinding.ActivityLoginBinding
import com.devper.template.presentation.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var navController: NavController
    private lateinit var host: NavHostFragment

    private var isWaitForSecondBackClick = false
    private var currentId: Int = 0

    private val progress: ProgressHudDialog by lazy {
        ProgressHudDialog.init(this, "Loading...", false)
    }

    override fun setupView() {
        setSupportActionBar(binding.toolbar)
        host = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment? ?: return
        navController = host.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentId = destination.id
            binding.toolbar.title = destination.label
            applyDisplayHomeAsUpEnabled(haveBack(currentId))
            if (haveToolbar(currentId)) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }

    }

    override fun observeLiveData() {
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

}
