package com.devper.template.presentation.pinform

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentPinFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PinChangeFragment : BaseFragment<FragmentPinFormBinding>(R.layout.fragment_pin_form) {

    override val viewModel: PinChangeViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.pinCodeRoundView.onSuccess = {
            viewModel.verifyPin(it)
        }
        binding.pinCodeKeyboardView.onClick = {
            binding.pinCodeRoundView.setPin(it)
        }
    }

    override fun observeLiveData() {
        viewModel.resultVerify.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showDialog()
                }
                is ResultState.Success -> {
                    hideDialog()
                    viewModel.nextPage(it.data.actionToken)
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

}
