package com.devper.template.signup.ui

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.devper.template.R
import com.devper.template.appCompat
import com.devper.template.app.ui.BaseFragment
import com.devper.template.databinding.FragmentSignupBinding
import com.devper.template.handlerResponse
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>() {

    override fun getLayout() = R.layout.fragment_signup
    override fun initViewModel() = currentScope.getViewModel<SignupViewModel>(this)

    override fun setupView() {
        appCompat().supportActionBar?.hide()
        binding.viewModel = viewModel
        activity?.let {

        }
    }

    override fun setObserve() {
        with(viewModel) {
            results.observe(viewLifecycleOwner, Observer { resource ->
                if (resource != null) {
                    val result = handlerResponse(resource)
                    result?.data?.let {
                        findNavController().popBackStack()
                    }
                }
            })
        }
    }

    private fun next() {

    }

}
