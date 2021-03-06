package com.devper.template.presentation.pinforgot

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentPinFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ForgotPinFormFragment : BaseFragment<FragmentPinFormBinding>(R.layout.fragment_pin_form) {

    override val viewModel: ForgotPinFormViewModel by viewModels()

    override fun setupView() {
        binding.pinCodeRoundView.onSuccess = {
            viewModel.setPin(it)
        }
        binding.pinCodeKeyboardView.onClick = {
            binding.pinCodeRoundView.setPin(it)
        }
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(EXTRA_PARAM)
        Timber.i("param : %s", param)
        param?.let {
            viewModel.actionToken = it
        }
    }

    override fun observeLiveData() {
        viewModel.resultSetPin.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    viewModel.nextPage()
                }
                is ResultState.Error -> {
                    hideDialog()
                    toError(it.throwable.toError())
                }
            }
        })
    }

}
