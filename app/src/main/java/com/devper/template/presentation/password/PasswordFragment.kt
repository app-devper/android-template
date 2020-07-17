package com.devper.template.presentation.password

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.AppConfig.EXTRA_PARAM
import com.devper.template.R
import com.devper.template.databinding.FragmentSetPasswordBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PasswordFragment : BaseFragment<FragmentSetPasswordBinding>(R.layout.fragment_set_password) {

    override val viewModel: PasswordViewModel by viewModel()

    override fun setupView() {
        hideToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun onArguments(it: Bundle?) {
        val param = it?.getString(EXTRA_PARAM)
        param?.let {
            viewModel.actionToken = it
        }
    }

    override fun observeLiveData() {
        viewModel.resultSetPassword.observe(viewLifecycleOwner, Observer {
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
