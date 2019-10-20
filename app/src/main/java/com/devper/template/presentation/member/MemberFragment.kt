package com.devper.template.presentation.member

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.databinding.FragmentMemberBinding
import com.devper.template.presentation.BaseFragment
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MemberFragment : BaseFragment<FragmentMemberBinding>(R.layout.fragment_member) {

    private lateinit var adapter: MemberAdapter

    val viewModel = currentScope.getViewModel<MemberViewModel>(this)

    override fun setupView() {
        binding.viewModel = viewModel
        adapter = MemberAdapter {
            viewModel.retry()
        }
        binding.list.adapter = adapter
        viewModel.getMember()
    }

    override fun setObserve() {
        with(viewModel) {

            memberList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setNetworkState(it)
            })
        }
    }

}
