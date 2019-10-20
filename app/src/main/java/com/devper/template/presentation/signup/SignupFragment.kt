package com.devper.template.presentation.signup

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentSignupBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.core.appCompat
import com.devper.template.presentation.core.hideLoading
import com.devper.template.presentation.core.showLoading
import com.devper.template.presentation.core.toError
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : BaseFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    val viewModel: SignupViewModel by viewModel()

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        binding.viewModel = viewModel
    }

    override fun setObserve() {
        with(viewModel) {
            results.observe(viewLifecycleOwner, Observer {
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
    }

    private fun next() {

    }

}
