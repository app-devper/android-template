package com.devper.template.presentation.otp

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentOtpVerifyBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.model.otp.VerifyUserParam
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class OtpVerifyFragment : BaseFragment<FragmentOtpVerifyBinding>(R.layout.fragment_otp_verify) {

    override val viewModel: OtpVerifyViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
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
        viewModel.resultVerifyUser.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    viewModel.setVerifyUser(it.data)
                }
                is ResultState.Error -> {
                    toError(it.throwable)
                }
            }
        })

        viewModel.resultVerifyCode.observe(viewLifecycleOwner, Observer {
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
                    toError(it.throwable)
                }
            }
        })
    }

}
