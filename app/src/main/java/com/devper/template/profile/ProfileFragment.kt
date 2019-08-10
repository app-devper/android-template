package com.devper.template.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import com.devper.template.common.ui.BaseFragment
import com.devper.imagepicker.REQUEST_IMAGE
import com.devper.imagepicker.clearCache
import com.devper.imagepicker.showImagePickerOptions
import com.devper.template.R
import com.devper.template.databinding.FragmentProfileBinding
import com.devper.template.MainViewModel
import com.devper.template.appCompat

class ProfileFragment : BaseFragment<FragmentProfileBinding, MainViewModel>() {

    override fun getLayout() = R.layout.fragment_profile

    override fun initViewModel() = getSharedViewModel<MainViewModel>()

    override fun setupView() {
        appCompat().supportActionBar?.show()
        binding.imgProfile.setOnClickListener {
            showImagePickerOptions(activity!!)
        }
        binding.imgPlus.setOnClickListener {
            showImagePickerOptions(activity!!)
        }
        clearCache(context!!)
    }

    override fun setObserve() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val uri = data?.getParcelableExtra<Uri>("path")
                    loadProfile(uri.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun loadProfile(url: String) {
        Glide.with(this).load(url).into(binding.imgProfile)
        binding.imgProfile.setColorFilter(ContextCompat.getColor(context!!, android.R.color.transparent))
    }

}
