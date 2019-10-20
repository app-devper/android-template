package com.devper.template.presentation.login

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.devper.fingerprint.BiometricController
import com.devper.fingerprint.Callback
import com.devper.smartlogin.*
import com.devper.smartlogin.users.SmartUser
import com.devper.smartlogin.util.SmartLoginException
import com.devper.template.R
import com.devper.template.core.ext.makeLinks
import com.devper.template.databinding.FragmentLoginBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.core.*
import com.devper.template.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login), SmartLoginCallbacks, Callback {

    private lateinit var config: SmartLoginConfig
    private lateinit var biometric: BiometricController
    private lateinit var smartLogin: SmartLogin

    private val loginViewModel: LoginViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        binding.viewModel = loginViewModel
        binding.tvSignup.makeLinks(Pair(getString(R.string.signup_button), View.OnClickListener {
            findNavController().navigate(LoginFragmentDirections.signupAction())
        }))
        loginViewModel.clearUser()
        activity?.let {
            config = SmartLoginConfig(it, this)
            biometric = BiometricController(it, this)
            config.facebookAppId = getString(R.string.facebook_app_id)
            config.lineChannelId = "1598367163"
            biometric.authenticate()
        }
    }

    override fun setObserve() {
        with(loginViewModel) {
            results.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }
                    is ResultState.Success -> {
                        hideLoading()
                        mainViewModel.user.value = it.data
                        next()
                    }
                    is ResultState.Error -> {
                        hideLoading()
                        toError(it.throwable)
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
//        val newUser = User(user.userId ?: return).apply {
//            firstName = user.firstName
//            lastName = user.lastName
//            email = user.email
//            imageUrl = user.profileLink
//        }
//        when (user) {
//            is SmartFacebookUser -> newUser.username = user.firstName + " " + user.lastName
//            is SmartLineUser -> newUser.username = user.displayName
//            is SmartGoogleUser -> newUser.username = user.displayName
//        }

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
