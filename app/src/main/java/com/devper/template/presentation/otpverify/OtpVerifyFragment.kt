package com.devper.template.presentation.otpverify

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentOtpVerifyBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class OtpVerifyFragment : BaseFragment<FragmentOtpVerifyBinding>(R.layout.fragment_otp_verify) {

    override val viewModel: OtpVerifyViewModel by viewModels()

    override fun setupView() {
        binding.viewModel = viewModel
        binding.pinCodeKeyboardView.apply {
            onClick = {
                viewModel.setOtp(it)
            }
        }
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getParcelable<VerifyUserParam>(EXTRA_PARAM)
        param?.let {
            viewModel.verifyUser(it)
        }
        Timber.i("param : %s", param)
    }

    override fun observeLiveData() {
        viewModel.resultVerifyUser.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    viewModel.setVerifyUser(it.data)
                }
                is ResultState.Error -> {
                    val throwable = it.throwable.toError()
                    showMessage(throwable.resultCode, throwable.getDesc()) {
                        viewModel.popBackStack()
                    }
                }
            }
        })

        viewModel.resultVerifyCode.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    viewModel.nextPage(it.data)
                }
                is ResultState.Error -> {
                    hideDialog()
                    viewModel.clearCode()
                    val throwable = it.throwable.toError()
                    showMessage(throwable.resultCode, throwable.getDesc()) { code ->
                        when (code) {
                            "CM-401-108" -> {
                                viewModel.retry()
                            }
                        }
                    }
                }
            }
        })
    }

}
