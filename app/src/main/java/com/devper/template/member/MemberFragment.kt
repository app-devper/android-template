package com.devper.template.member

import androidx.lifecycle.Observer
import com.devper.template.R
import com.devper.template.common.ui.BaseFragment
import com.devper.template.databinding.FragmentMemberBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MemberFragment : BaseFragment<FragmentMemberBinding, MemberViewModel>() {

    private lateinit var adapter: MemberAdapter

    override fun getLayout() = R.layout.fragment_member

    override fun initViewModel() = getViewModel<MemberViewModel>()

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
