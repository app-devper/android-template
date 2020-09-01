package com.devper.template.presentation.notification

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.devper.template.R
import com.devper.template.databinding.FragmentNotificationBinding
import com.devper.template.domain.model.notification.Notification
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.notification.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {

    override val viewModel: NotificationViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        viewModel.adapter.apply {
            onClick = {

            }
            addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }
    }

    override fun observeLiveData() {
        mainViewModel.messageLiveData.observe(this, {
            viewModel.adapter.refresh()
        })
    }

    private fun setAdapter(pagingData :Flow<PagingData<Notification>>){
        lifecycleScope.launchWhenCreated {
            pagingData.collectLatest {
                viewModel.adapter.submitData(it)
            }
        }
    }

    override fun onArguments(it: Bundle?) {
        setAdapter(viewModel.getNotifications())
    }
}
