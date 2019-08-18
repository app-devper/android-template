package com.devper.template.common.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.devper.common.helper.LocaleHelper

abstract class BaseActivity<Binding : ViewDataBinding, Model : ViewModel> : AppCompatActivity() {

    lateinit var binding: Binding
    lateinit var viewModel: Model

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        binding.lifecycleOwner = this
        viewModel = initViewModel()

        setupView()
        setObserve()
    }

    abstract fun getLayout(): Int

    abstract fun initViewModel(): Model

    abstract fun setupView()

    abstract fun setObserve()

}
