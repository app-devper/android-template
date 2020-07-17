package com.devper.template.presentation

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.devper.template.core.platform.helper.LocaleHelper

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
        observeLiveData()
    }

    abstract fun setupView()

    abstract fun observeLiveData()

    fun applyDisplayHomeAsUpEnabled(asUpEnable: Boolean) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(asUpEnable)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

}
