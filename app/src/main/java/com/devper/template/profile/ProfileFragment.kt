package com.devper.template.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.devper.imagepicker.REQUEST_IMAGE
import com.devper.imagepicker.clearCache
import com.devper.imagepicker.showImagePickerOptions
import com.devper.template.R
import com.devper.template.appCompat
import com.devper.template.common.AppDatabase
import com.devper.template.common.ui.BaseFragment
import com.devper.template.databinding.FragmentProfileBinding
import com.devper.template.handlerResponse
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val db: AppDatabase = get()

    override fun getLayout() = R.layout.fragment_profile

    override fun initViewModel() = getViewModel<ProfileViewModel>()

    override fun setupView() {
        appCompat().supportActionBar?.show()
        with(binding) {
            imgProfile.setOnClickListener {
                showImagePickerOptions(activity!!)
            }
            imgPlus.setOnClickListener {
                showImagePickerOptions(activity!!)
            }
        }
        clearCache(context!!)

        val user = db.user().getFirstUser()
        user?.let {
            loadProfile(it.imageUrl)
            viewModel.getProfile(it.id)
        }
    }

    override fun setObserve() {
        with(viewModel) {
            results.observe(viewLifecycleOwner, Observer { resource ->
                if (resource != null) {
                    val result = handlerResponse(resource)
                }
            })
        }
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

    private fun loadProfile(url: String?) {
        if (url == null) {
            return
        }
        Glide.with(this).load(url).into(binding.imgProfile)
        binding.imgProfile.setColorFilter(ContextCompat.getColor(context!!, android.R.color.transparent))
    }

}
