package com.devper.template.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devper.template.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.devper.template.common.ui.BaseFragment
import com.devper.fingerprint.BiometricController
import com.devper.fingerprint.Callback
import com.devper.smartlogin.*
import com.devper.smartlogin.users.SmartFacebookUser
import com.devper.smartlogin.users.SmartGoogleUser
import com.devper.smartlogin.users.SmartLineUser
import com.devper.smartlogin.users.SmartUser
import com.devper.smartlogin.util.SmartLoginException
import com.devper.template.R
import com.devper.template.common.AppDatabase
import com.devper.template.databinding.FragmentLoginBinding
import com.devper.template.common.model.User
import timber.log.Timber

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), SmartLoginCallbacks, Callback {

    private lateinit var config: SmartLoginConfig
    private lateinit var biometric: BiometricController
    private var smartLogin: SmartLogin? = null
    private var db: AppDatabase = get()

    override fun getLayout() = R.layout.fragment_login
    override fun initViewModel() = getViewModel<LoginViewModel>()

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        binding.viewModel = viewModel
        config = SmartLoginConfig(activity!!, this)
        biometric = BiometricController(activity!!, this)
        config.facebookAppId = getString(R.string.facebook_app_id)
        config.lineChannelId = "1598367163"
        db.user().clearUser()
        biometric.authenticate()
    }

    override fun setObserve() {
        with(viewModel){
            results.observe(viewLifecycleOwner, Observer { resource ->
                if (resource != null) {
                    val users = handlerResponse(resource)
                    users?.let {
                        if (it.isNotEmpty()) {
                            Timber.i("Response:%s", users[0])
                            next()
                        }
                    }
                }
            })

            login.observe(viewLifecycleOwner, Observer {
                when (it) {
                    "Line" -> {
                        smartLogin = SmartLoginFactory.build(LoginType.Line)
                        smartLogin?.login(config)
                    }
                    "Facebook" -> {
                        smartLogin = SmartLoginFactory.build(LoginType.Facebook)
                        smartLogin?.login(config)
                    }
                    "Google" -> {
                        smartLogin = SmartLoginFactory.build(LoginType.Google)
                        smartLogin?.login(config)
                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        smartLogin?.let {
            showLoading()
            it.onActivityResult(requestCode, resultCode, data, config)
        }
    }

    override fun onLoginSuccess(user: SmartUser) {
        Log.i("LoginSuccess", "SmartUser: $user")
        val newUser = User(user.userId!!).apply {
            firstName = user.firstName
            lastName = user.lastName
            email = user.email
            imageUrl = user.profileLink
        }
        when (user) {
            is SmartFacebookUser -> newUser.username = user.firstName + " " + user.lastName
            is SmartLineUser -> newUser.username = user.displayName
            is SmartGoogleUser -> newUser.username = user.displayName
        }
        db.user().addUser(newUser).also {
            hideLoading()
            next()
        }
    }

    override fun onLoginFailure(e: SmartLoginException) {
        hideLoading()
        appCompat().showMessage(e.message!!)
    }

    override fun onAuthenticated() {

    }

    override fun onError() {

    }

    private fun next() {
        val navBuilder = NavOptions.Builder()
        val navOptions = navBuilder.setPopUpTo(R.id.login_dest, true).build()
        val user =  db.user().getFirstUser()
        if (user != null) {
            findNavController().navigate(R.id.home_dest, null, navOptions)
        }
    }

}
