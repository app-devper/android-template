package com.devper.template.presentation.term

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentTermConditionBinding
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermConditionFragment : BaseFragment<FragmentTermConditionBinding>(R.layout.fragment_term_condition) {

    override val viewModel: BaseViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
    }

    override fun observeLiveData() {

    }

    override fun onArguments(it: Bundle?) {}

}
