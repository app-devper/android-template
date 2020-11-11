package com.devper.template.presentation.pinchange

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.core.extension.toInvisible
import com.devper.template.databinding.FragmentPinFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinVerifyFragment : BaseFragment<FragmentPinFormBinding>(R.layout.fragment_pin_form) {

    override val viewModel: PinVerifyViewModel by viewModels()

    override fun setupView() {
        binding.tvTitle.setText(R.string.pin_confirm)
        binding.tvDescription.toInvisible()
        binding.pinCodeRoundView.onSuccess = {
            viewModel.verifyPin(it)
        }
        binding.pinCodeKeyboardView.onClick = {
            binding.pinCodeRoundView.setPin(it)
        }
    }

    override fun observeLiveData() {
        viewModel.resultVerify.observe(viewLifecycleOwner, {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    checkFlow(it.data.actionToken)
                }
                is ResultState.Error -> {
                    hideDialog()
                    binding.pinCodeRoundView.clearPin()
                    toError(it.throwable.toError())
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {
    }

    private fun checkFlow(actionToken: String) {
        when (viewModel.flow) {
            AppConfig.FLOW_CHANGE_PIN -> {
                viewModel.nextPage(actionToken)
            }
            else -> {
                viewModel.popBackStack()
            }
        }
    }

}
