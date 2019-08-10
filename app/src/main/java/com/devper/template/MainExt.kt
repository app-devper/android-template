package com.devper.template

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.SparseArray
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.devper.common.api.Resource
import com.devper.common.api.Status
import com.devper.template.common.util.ServiceHelper
import com.devper.template.widget.ConfirmDialog

var progress: Dialog? = null

fun initProgress(dialog: Dialog) {
    progress = dialog
}

fun <T> AppCompatActivity.handlerResponse(response: Resource<T>?): T? {
    hideLoading()
    if (response == null) {
        return null
    }
    return when (response.status) {
        Status.SUCCESS -> {
            response.data
        }
        Status.ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.LOADING -> {
            showLoading()
            response.data
        }
        Status.TIMEOUT -> {
            showMessage(response.message)
            response.data
        }
        Status.CONVERTION_ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.OTHER_ERROR -> {
            showMessage(response.message)
            response.data
        }
    }
}

fun <T> AppCompatActivity.handlerResponseOnly(response: Resource<T>?): T? {
    if (response == null) {
        return null
    }
    return when (response.status) {
        Status.SUCCESS -> {
            response.data
        }
        Status.ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.LOADING -> {
            response.data
        }
        Status.TIMEOUT -> {
            showMessage(response.message)
            response.data
        }
        Status.CONVERTION_ERROR -> {
            showMessage(response.message)
            response.data
        }
        Status.OTHER_ERROR -> {
            showMessage(response.message)
            response.data
        }
    }
}

fun Fragment.appCompat(): AppCompatActivity {
    return activity as AppCompatActivity
}

fun <T> Fragment.handlerResponseOnly(response: Resource<T>?): T? {
    return appCompat().handlerResponseOnly(response)
}

fun <T> Fragment.handlerResponse(response: Resource<T>?): T? {
    hideLoading()
    return appCompat().handlerResponse(response)
}

fun showLoading() {
    progress?.let {
        if (!it.isShowing) {
            it.show()
        }
    }
}

fun hideLoading() {
    progress?.let {
        if (it.isShowing) {
            it.dismiss()
        }
    }
}

fun AppCompatActivity.showMessage(message: String?) {

    showMessageTag(message?: "Error", "dialog")
}

fun AppCompatActivity.showConfirmMessage(title: String, message: String, tag: String) {
    val fragment = ConfirmDialog.Builder().setTitle(title).setMessage(message).setPositive(android.R.string.yes).setNegative(android.R.string.no).build()
    fragment.show(supportFragmentManager, tag)
}

fun AppCompatActivity.showMessageTag(message: String, tag: String) {
    showMessageTagTitle("Warning", message, tag)
}

fun AppCompatActivity.showMessageTagTitle(title: String, message: String, tag: String) {
    val fragment = ConfirmDialog.Builder().setTitle(title).setMessage(message).setPositive(android.R.string.ok).build()
    fragment.show(supportFragmentManager, tag)
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

//Navigation Extension
fun BottomNavigationView.setupWithNavController(navGraphIds: List<Int>, fragmentManager: FragmentManager, containerId: Int, intent: Intent): LiveData<NavController> {

    // Map of tags
    val graphIdToTagMap = SparseArray<String>()
    // Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()

    var firstFragmentGraphId = 0

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphId, containerId)

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        if (index == 0) {
            firstFragmentGraphId = graphId
        }

        // Save to the map
        graphIdToTagMap[graphId] = fragmentTag

        // Attach or detach nav host fragment depending on whether it's the selected item.
        if (this.selectedItemId == graphId) {
            // Update livedata with the selected graph
            selectedNavController.value = navHostFragment.navController
            attachNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            detachNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    // When a navigation item is selected
    setOnNavigationItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(firstFragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment

                // Exclude the first fragment tag because it's always in the back stack.
                if (firstFragmentTag != newlySelectedItemTag) {
                    // Commit a transaction that cleans the back stack and adds the first fragment
                    // to it, creating the fixed started destination.
                    fragmentManager.beginTransaction().attach(selectedFragment).setPrimaryNavigationFragment(selectedFragment).apply {
                        // Detach all other Fragments
                        graphIdToTagMap.forEach { _, fragmentTagIter ->
                            if (fragmentTagIter != newlySelectedItemTag) {
                                detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                            }
                        }
                    }.addToBackStack(firstFragmentTag)
                        //.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                        .setReorderingAllowed(true).commit()
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Optional: on item reselected, pop back stack to the destination of the graph
    setupItemReselected(graphIdToTagMap, fragmentManager)

    // Handle deep link
    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun BottomNavigationView.setupDeepLinks(navGraphIds: List<Int>, fragmentManager: FragmentManager, containerId: Int, intent: Intent) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphId, containerId)
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent) && selectedItemId != navHostFragment.navController.graph.id) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(graphIdToTagMap: SparseArray<String>, fragmentManager: FragmentManager) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag) as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(navController.graph.startDestination, false)
    }
}

private fun detachNavHostFragment(fragmentManager: FragmentManager, navHostFragment: NavHostFragment) {
    fragmentManager.beginTransaction().detach(navHostFragment).commitNow()
}

private fun attachNavHostFragment(fragmentManager: FragmentManager, navHostFragment: NavHostFragment, isPrimaryNavFragment: Boolean) {
    fragmentManager.beginTransaction().attach(navHostFragment).apply {
        if (isPrimaryNavFragment) {
            setPrimaryNavigationFragment(navHostFragment)
        }
    }.commitNow()
}

private fun obtainNavHostFragment(fragmentManager: FragmentManager, fragmentTag: String, navGraphId: Int, containerId: Int): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction().add(containerId, navHostFragment, fragmentTag).commitNow()
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"
