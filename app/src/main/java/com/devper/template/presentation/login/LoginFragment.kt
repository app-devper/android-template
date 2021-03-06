package com.devper.template.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.core.extension.makeLinks
import com.devper.template.core.platform.biometric.BiometricController
import com.devper.template.core.smartlogin.*
import com.devper.template.core.smartlogin.users.SmartUser
import com.devper.template.core.smartlogin.util.SmartLoginException
import com.devper.template.databinding.FragmentLoginBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login),
    SmartLoginCallbacks, BiometricController.Callback {

    private lateinit var config: SmartLoginConfig
    private lateinit var biometric: BiometricController
    private lateinit var smartLogin: SmartLogin

    override val viewModel: LoginViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
        binding.tvSignup.makeLinks(Pair(getString(R.string.signup_button), View.OnClickListener {
            viewModel.nextToSignUp()
        }))

        activity?.let {
            config = SmartLoginConfig(it, this)
            biometric = BiometricController(it, this)
            config.facebookAppId = getString(R.string.facebook_app_id)
            config.lineChannelId = "1598367163"
            biometric.authenticate()
        }
    }

    override fun onArguments(it: Bundle?) {
        viewModel.clearUser()
        clearLogin()
    }

    override fun observeLiveData() {
        viewModel.resultsLogin.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    mainViewModel.initLogin()
                }
                is ResultState.Error -> {
                    hideDialog()
                    val throwable = it.throwable.toError()
                    showMessage(throwable.resultCode, throwable.getDesc()) {

                    }
                }
            }
        })

        viewModel.login.observe(this, {
            if (it != LoginType.Custom) {
                smartLogin = SmartLoginFactory.build(it).apply {
                    login(config)
                }
            }
        })

        mainViewModel.loginLiveData.observe(this, {
            if (it) {
                viewModel.nextToHome()
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        smartLogin.run {
            showLoading()
            onActivityResult(requestCode, resultCode, data, config)
        }
    }

    override fun onLoginSuccess(user: SmartUser) {
    }

    override fun onLoginFailure(e: SmartLoginException) {
        hideLoading()
    }

    override fun onAuthenticated() {

    }

    override fun onError() {

    }

}
