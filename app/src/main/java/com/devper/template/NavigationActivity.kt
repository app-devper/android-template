package com.devper.template

import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.MenuItem
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
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.devper.template.common.ui.BaseActivity
import com.devper.fcm.MessagingHandler
import com.devper.template.common.AppDatabase
import com.devper.template.widget.ProgressHudDialog
import com.devper.template.common.pref.AppPreference
import com.devper.template.R
import com.devper.template.databinding.ActivityNavigationBinding
import com.devper.template.databinding.LayoutNavHeaderBinding

class NavigationActivity : BaseActivity<ActivityNavigationBinding, MainViewModel>() {

    private lateinit var navController: NavController
    private lateinit var host: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val db: AppDatabase = get()
    private lateinit var navHeaderMainBinding: LayoutNavHeaderBinding
    private lateinit var messagingHandler: MessagingHandler

    override fun getLayout() = R.layout.activity_navigation
    override fun initViewModel() = getViewModel<MainViewModel>()

    override fun setupView() {
        initProgress(ProgressHudDialog.init(this, "Loading...", false))
        setSupportActionBar(binding.toolbar)
        host = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment? ?: return
        // Set up Action Bar
        navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }
            if (destination.id == R.id.home_dest) {
                appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest), binding.drawerLayout)
                setupActionBar(navController, appBarConfiguration)
                setupNavigationMenu(navController)
                setupNavMenu()
            }
            Log.d("NavigationActivity", "Navigated to $dest")
        }
        messagingHandler = MessagingHandler(this, lifecycle)
    }

    override fun setObserve() {
        messagingHandler.message.observe(this, Observer {
            val title = it.getString("title")
            val body = it.getString("body")
            showMessageTagTitle(title!!, body!!, "push")
        })
        messagingHandler.token.observe(this, Observer {
            AppPreference.getInstance().fbToken = it
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = host.childFragmentManager.primaryNavigationFragment
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setupNavigationMenu(navController: NavController) {
        binding.navView.setNavigationItemSelectedListener {
            val handled = onNavDestinationSelected(it, navController)
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
        }
        val dao = db.user().getFirstUser()
        dao?.let {
            navHeaderMainBinding.user = it
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

    private fun onNavDestinationSelected(item: MenuItem, navController: NavController): Boolean {
        val options = NavOptions.Builder().run {
            setLaunchSingleTop(true)
            setEnterAnim(R.anim.nav_default_enter_anim)
            setExitAnim(R.anim.nav_default_exit_anim)
            setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            if (item.itemId == R.id.login_dest) {
                setPopUpTo(R.id.home_dest, true)
            }
            build()
        }
        return try {
            navController.navigate(item.itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

}
