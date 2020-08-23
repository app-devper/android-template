package com.devper.template.presentation.pin

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.R
import com.devper.template.databinding.FragmentPinMaxAttemptBinding
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinMaxAttemptFragment : BaseFragment<FragmentPinMaxAttemptBinding>(R.layout.fragment_pin_max_attempt) {

    override val viewModel: PinMaxAttemptViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
    }

    override fun observeLiveData() {

    }

    override fun onArguments(it: Bundle?) {

    }
}