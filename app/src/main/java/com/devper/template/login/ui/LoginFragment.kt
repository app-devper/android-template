package com.devper.template.login.ui

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devper.fingerprint.BiometricController
import com.devper.fingerprint.Callback
import com.devper.smartlogin.*
import com.devper.smartlogin.users.SmartFacebookUser
import com.devper.smartlogin.users.SmartGoogleUser
import com.devper.smartlogin.users.SmartLineUser
import com.devper.smartlogin.users.SmartUser
import com.devper.smartlogin.util.SmartLoginException
import com.devper.template.*
import com.devper.template.R
import com.devper.template.app.db.AppDatabase
import com.devper.template.app.ext.makeLinks
import com.devper.template.app.model.User
import com.devper.template.app.pref.AppPreference
import com.devper.template.app.ui.BaseFragment
import com.devper.template.databinding.FragmentLoginBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.getViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(), SmartLoginCallbacks, Callback {

    private lateinit var config: SmartLoginConfig
    private lateinit var biometric: BiometricController
    private lateinit var smartLogin: SmartLogin
    private val db: AppDatabase by inject()
    private val pref: AppPreference by inject()

    override fun getLayout() = R.layout.fragment_login

    override fun initViewModel() = currentScope.getViewModel<LoginViewModel>(this)

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        binding.viewModel = viewModel
        binding.tvSignup.makeLinks(Pair(getString(R.string.signup_button), View.OnClickListener {
            findNavController().navigate(LoginFragmentDirections.signupAction())
        }))
        db.user().clearUser()
        activity?.let{
            config = SmartLoginConfig(it, this)
            biometric = BiometricController(it, this)
            config.facebookAppId = getString(R.string.facebook_app_id)
            config.lineChannelId = "1598367163"
            biometric.authenticate()
        }
    }

    override fun setObserve() {
        with(viewModel){
            results.observe(viewLifecycleOwner, Observer { resource ->
                if (resource != null) {
                    val result = handlerResponse(resource)
                    result?.data?.let {
                        pref.token = it.accessToken
                        db.user().addUser(it.user)
                        next()
                    }
                }
            })

            login.observe(viewLifecycleOwner, Observer {
                if (it != LoginType.Custom) {
                    smartLogin = SmartLoginFactory.build(it).apply {
                        login(config)
                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        smartLogin.run {
            showLoading()
            onActivityResult(requestCode, resultCode, data, config)
        }
    }

    override fun onLoginSuccess(user: SmartUser) {
        Log.i("LoginSuccess", "SmartUser: $user")
        val newUser = User(user.userId ?: return).apply {
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
        appCompat().showMessage(e.message)
    }

    override fun onAuthenticated() {

    }

    override fun onError() {

    }

    private fun next() {
        val navBuilder = NavOptions.Builder()
        val navOptions = navBuilder.setPopUpTo(R.id.login_dest, true).build()
        findNavController().navigate(R.id.home_dest, null, navOptions)
    }

}
