package com.devper.template.presentation.pin

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.core.extension.toInvisible
import com.devper.template.data.preference.AppPreference
import com.devper.template.databinding.FragmentPinFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.pin.viewmodel.PinChangeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PinChangeFragment : BaseFragment<FragmentPinFormBinding>(R.layout.fragment_pin_form) {

    @Inject
    lateinit var perf: AppPreference
    override val viewModel: PinChangeViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
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
                    toError(it.throwable)
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {

    }

    private fun checkFlow(actionToken: String) {
        if (viewModel.flow == AppConfig.FLOW_SET_BIO) {
            if (perf.isSetPin) {
                perf.pin = ""
            } else {
                perf.pin = binding.pinCodeRoundView.pin
            }
            viewModel.popBackStack.invoke()
        } else {
            viewModel.nextPage(actionToken)
        }
    }

}
