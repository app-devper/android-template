package com.devper.template.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.devper.template.presentation.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment<Binding : ViewDataBinding>(private val layoutId: Int) : Fragment() {

    lateinit var binding: Binding

    val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupView()
        observeLiveData()
    }

    abstract fun setupView()

    abstract fun observeLiveData()

}
