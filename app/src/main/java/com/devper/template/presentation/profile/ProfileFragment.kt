package com.devper.template.presentation.profile

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.api.load
import com.devper.template.R
import com.devper.template.core.picker.ImagePickerConfig
import com.devper.template.core.picker.PickerCallback
import com.devper.template.databinding.FragmentProfileBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import com.devper.template.presentation.main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val profileViewModel: ProfileViewModel by viewModel()
    private val mainViewModel: MainViewModel by sharedViewModel()
    private lateinit var picker: ImagePickerConfig

    override fun setupView() {
        appCompat().supportActionBar?.show()
        picker = ImagePickerConfig(requireActivity(), object : PickerCallback {
            override fun onSuccess(imagePath: Uri?) {
                loadProfile(imagePath.toString())
            }

            override fun onCancel() {}
        })
        with(binding) {
            imgProfile.setOnClickListener {
                picker.chooseImageFromGallery()
            }
            imgPlus.setOnClickListener {
                picker.chooseImageFromGallery()
            }
        }
        //clearCache(requireContext())

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
        picker.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadProfile(url: String?) {
        if (url == null) {
            return
        }
        binding.imgProfile.load(url)
        binding.imgProfile.setColorFilter(ContextCompat.getColor(requireContext(), android.R.color.transparent))
    }

}
