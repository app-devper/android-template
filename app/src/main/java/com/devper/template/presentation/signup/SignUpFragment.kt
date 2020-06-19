package com.devper.template.presentation.signup

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentSignupBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.appCompat
import com.devper.template.presentation.main.hideLoading
import com.devper.template.presentation.main.showLoading
import com.devper.template.presentation.main.toError
import com.devper.template.presentation.signup.viewmodel.SignupViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    val viewModel: SignupViewModel by viewModel()

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.results.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    next()
                }
                is ResultState.Error -> {
                    hideLoading()
                    toError(it.throwable)
                }
            }
        })
    }

    private fun next() {

    }

}
