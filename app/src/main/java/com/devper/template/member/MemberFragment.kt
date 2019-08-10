package com.devper.template.member

import android.util.Log
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.devper.template.common.ui.BaseFragment
import com.devper.template.R
import com.devper.template.databinding.FragmentMemberBinding
import com.devper.template.handlerResponse

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
            results.observe(viewLifecycleOwner, Observer {
                val result = handlerResponse(it)
                Log.i("MemberFragment", "Results: " + result.toString())
            })

            memberList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })

            networkState.observe(viewLifecycleOwner, Observer {
                adapter.setNetworkState(it)
            })
        }
    }

}
