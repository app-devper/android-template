package com.devper.template.presentation.main

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.devper.fcm.BadgeHelper
import com.devper.fcm.MessagingHandler
import com.devper.template.R
import com.devper.template.core.widget.ConfirmDialog
import com.devper.template.core.widget.ProgressHudDialog
import com.devper.template.databinding.ActivityNavigationBinding
import com.devper.template.databinding.LayoutNavHeaderBinding
import com.devper.template.presentation.BaseActivity
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity<ActivityNavigationBinding>(R.layout.activity_navigation), ConfirmDialog.OnDialogListener {

    private lateinit var navController: NavController
    private lateinit var host: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHeaderMainBinding: LayoutNavHeaderBinding
    private lateinit var messagingHandler: MessagingHandler
    private val mainViewModel: MainViewModel by viewModel()

    val progress: ProgressHudDialog by currentScope.inject { parametersOf(this) }

    override fun setupView() {

        setSupportActionBar(binding.toolbar)
        host = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment? ?: return
        // Set up Action Bar
        navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        binding.drawerLayout.drawerElevation = 0F
        val actionBarDrawerToggle = object :
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.main_open_drawer, R.string.main_close_drawer) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                binding.content.translationX = slideX
            }
        }

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
            if (destination.id == R.id.home_dest) {
                appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest), binding.drawerLayout)
                setupActionBar(navController, appBarConfiguration)
                setupNavigationMenu()
                setupNavMenu()
            }
            Log.d("MainActivity", "Navigated to $dest")
        }
        messagingHandler = MessagingHandler(this, lifecycle)
        messagingHandler.subscribeToTopic("all")
    }

    override fun setObserve() {
        messagingHandler.message.observe(this, Observer {
            val title = it.getString("title") ?: "Warning"
            val body = it.getString("body")
            val destination = it.getString("destination")
            setBadge()
            body?.let {
                if (destination != null) {
                    showConfirmMessage(title, body, destination)
                } else {
                    showMessageTagTitle(title, body, "push")
                }
            }
        })
        messagingHandler.token.observe(this, Observer {
            //pref.fbToken = it
        })
        setBadge()
    }

    private fun setBadge() {
        val badgeCount = BadgeHelper(this).badgeCount.toString()
        mainViewModel.badge.postValue(badgeCount)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = host.childFragmentManager.primaryNavigationFragment
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupNavigationMenu() {
        binding.navView.setNavigationItemSelectedListener {
            val handled = onNavDestinationSelected(it)
            binding.drawerLayout.closeDrawer(binding.navView)
            handled
        }
    }

    private fun setupActionBar(navController: NavController, appBarConfig: AppBarConfiguration) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    private fun setupNavMenu() {
        val sideNavView = binding.navView
        if (sideNavView.headerCount == 0) {
            navHeaderMainBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_nav_header, sideNavView, false)
            sideNavView.addHeaderView(navHeaderMainBinding.root)
            mainViewModel.getUser()?.let {
                navHeaderMainBinding.user = it
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.host_fragment)) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.host_fragment).navigateUp(appBarConfiguration)
    }

    override fun onBackPressed() {
        val navDestination = navController.currentDestination
        if (navDestination != null && (navDestination.id == R.id.login_dest)) {
            finish()
            return
        }
        super.onBackPressed()
    }

    private fun onNavDestinationSelected(item: MenuItem): Boolean {
        return navigate(item.itemId)
    }

    private fun navigate(id: Int): Boolean {
        val options = NavOptions.Builder().run {
            setLaunchSingleTop(true)
            setEnterAnim(R.anim.nav_default_enter_anim)
            setExitAnim(R.anim.nav_default_exit_anim)
            setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            if (id == R.id.login_dest) {
                setPopUpTo(navController.currentDestination!!.id, true)
            }
            build()
        }
        return try {
            navController.navigate(id, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    override fun onPositiveClick(tag: String) {
        if (tag == "401") {
            navigate(R.id.login_dest)
        }
    }

    override fun onNegativeClick() {

    }

}
