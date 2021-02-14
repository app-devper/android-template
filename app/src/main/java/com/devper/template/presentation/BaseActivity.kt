package com.devper.template.presentation

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.devper.template.R
import com.devper.template.core.platform.helper.LocaleHelper

abstract class BaseActivity<Binding : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity() {

    lateinit var binding: Binding
    private var dialog: BaseDialogFragment? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, LocaleHelper.getLanguage(base)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this

        observeLiveData()
        setupView()
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

    fun showMessage(code: String = "", message: String = "",) {
        dialog?.let {
            if (it.isVisible) {
                return
            }
        }
        dialog = BaseDialogFragment.Builder().run {
            withTitle(getString(R.string.error_title))
            withoutCancel()
            withConfirmAction {

            }
            withDescription(if (code.isEmpty()) message else "$message [Code : $code]")
            build()
        }
        dialog?.show(supportFragmentManager, "dialog")
    }

}
