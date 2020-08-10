package com.devper.template.presentation.user

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.devper.template.R
import com.devper.template.databinding.FragmentUserBinding
import com.devper.template.domain.model.user.User
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {

    override val viewModel: UserViewModel by viewModels()

    override fun setupView() {
        showToolbar()
        hideBottomNavigation()
        binding.viewModel = viewModel
        viewModel.adapter.apply {
            onClick = {
                viewModel.nextToDetail(it.id)
            }
            addLoadStateListener { loadState ->
                if (loadState.refresh is LoadState.Loading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        }

        setAdapter(viewModel.getUsers())
    }

    override fun observeLiveData() {

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun setAdapter(pagingData :Flow<PagingData<User>>){
        lifecycleScope.launchWhenCreated {
            pagingData.collectLatest {
                viewModel.adapter.submitData(it)
            }
        }
    }

    override fun onArguments(it: Bundle?) {
        viewModel.getUsers()
    }
}
