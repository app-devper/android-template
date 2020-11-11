package com.devper.template.presentation.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import coil.load
import com.devper.template.R
import com.devper.template.core.platform.picker.ImagePickerConfig
import com.devper.template.core.platform.picker.PickerCallback
import com.devper.template.databinding.FragmentProfileBinding
import com.devper.template.domain.core.ResultState
import com.devper.template.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    override val viewModel: ProfileViewModel by viewModels()

    private lateinit var picker: ImagePickerConfig

    override fun setupView() {
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

    override fun onArguments(it: Bundle?) {
    }

    override fun observeLiveData() {
        mainViewModel.profileLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultState.Success -> {
                    binding.user = it.data
                }
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
        binding.imgProfile.setColorFilter(
            ContextCompat.getColor(requireContext(), android.R.color.transparent)
        )
    }

}
