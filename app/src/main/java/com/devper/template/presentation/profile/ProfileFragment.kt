package com.devper.template.presentation.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.api.load
import com.devper.imagepicker.REQUEST_IMAGE
import com.devper.imagepicker.clearCache
import com.devper.imagepicker.showImagePickerOptions
import com.devper.template.R
import com.devper.template.data.database.AppDatabase
import com.devper.template.databinding.FragmentProfileBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.appCompat
import com.devper.template.presentation.main.hideLoading
import com.devper.template.presentation.main.showLoading
import com.devper.template.presentation.main.toError
import com.devper.template.presentation.main.MainViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val db: AppDatabase = get()

    private val profileViewModel: ProfileViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()

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

    }

    override fun setObserve() {
        with(profileViewModel) {
            liveDataProfile.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ResultState.Loading -> {
                        showLoading()
                    }
                    is ResultState.Success -> {
                        hideLoading()
                    }
                    is ResultState.Error -> {
                        hideLoading()
                        toError(it.throwable)
                    }
                }
            })
        }
        mainViewModel.user.observe(this, Observer {
            it?.let {
                loadProfile(it.imageUrl)
                profileViewModel.getProfile(it.id)
            }
        })
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
        binding.imgProfile.load(url)
        binding.imgProfile.setColorFilter(ContextCompat.getColor(context!!, android.R.color.transparent))
    }

}
