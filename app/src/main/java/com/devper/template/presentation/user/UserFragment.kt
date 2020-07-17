package com.devper.template.presentation.user

import android.os.Bundle
import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentUserBinding
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    override val viewModel: UserViewModel by viewModel()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        viewModel.adapter.apply {
            onClick = {
                viewModel.nextToDetail(it.id)
            }
        }
    }

    override fun observeLiveData() {
        viewModel.userList.observe(viewLifecycleOwner, Observer {
            viewModel.adapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            viewModel.adapter.setNetworkState(it)
        })
        viewModel.isInitialLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    override fun onArguments(it: Bundle?) {
        viewModel.getUsers()
    }
}
