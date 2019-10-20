package com.devper.template.presentation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.devper.common.helper.LocaleHelper

abstract class BaseActivity<Binding : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {

    lateinit var binding: Binding

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        setupView()
        setObserve()
    }

    abstract fun setupView()

    abstract fun setObserve()

}
