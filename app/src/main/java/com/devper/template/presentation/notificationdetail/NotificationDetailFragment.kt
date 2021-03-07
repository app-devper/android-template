package com.devper.template.presentation.notificationdetail

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.devper.template.AppConfig
import com.devper.template.R
import com.devper.template.databinding.FragmentNotificationDetailBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.domain.core.toError
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDetailFragment : BaseFragment<FragmentNotificationDetailBinding>(R.layout.fragment_notification_detail) {

    override val viewModel: NotificationDetailViewModel by viewModels()

    override fun setupView() {
      binding.viewModel = viewModel
    }

    override fun observeLiveData() {
        viewModel.notificationResult.observe(this, {
            when (it) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success -> {
                    hideLoading()
                    viewModel.setNotification(it.data)
                }
                is ResultState.Error -> {
                    hideLoading()
                    toError(it.throwable.toError())
                }
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        if (mainViewModel.isLogin()) {
            val param = it?.getString(AppConfig.EXTRA_PARAM)
            param?.let {
                viewModel.getNotification(it)
            }
        } else {
            viewModel.onNavigate(R.id.action_to_login_pin, null)
        }
    }

}
