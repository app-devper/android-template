package com.devper.template.presentation.pinform

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentPinFormBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PinFormFragment : BaseFragment<FragmentPinFormBinding>(R.layout.fragment_pin_form) {

    override val viewModel: PinFormViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
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
        viewModel.resultSetPin.observe(viewLifecycleOwner, Observer {
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
                    toError(it.throwable)
                }
            }
        })
    }

}
