package com.devper.template.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<Binding : ViewDataBinding, Model : ViewModel> : Fragment() {

    lateinit var viewModel: Model
    lateinit var binding: Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel = initViewModel()
        }
        setupView()
        setObserve()
    }

    abstract fun getLayout(): Int

    abstract fun initViewModel(): Model

    abstract fun setupView()

    abstract fun setObserve()

}
