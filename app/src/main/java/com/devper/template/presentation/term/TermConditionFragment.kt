package com.devper.template.presentation.term

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentTermConditionBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermConditionFragment : BaseFragment<FragmentTermConditionBinding>(R.layout.fragment_term_condition) {

    override val viewModel: TermConditionViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.resultLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    viewModel.setTermCondition(it.data)
                    hideLoading()
                }
                is ResultState.Error -> {
                    hideLoading()
                    mainViewModel.error(it.throwable)
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        viewModel.getTermCondition()
    }

}
