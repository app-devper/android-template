package com.devper.template.profile

import com.devper.template.profile.data.ProfileRepository
import com.devper.template.profile.ui.ProfileFragment
import com.devper.template.profile.ui.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileModule = module {
    factory { ProfileRepository(get(), get()) }
    scope(named<ProfileFragment>()) {
        viewModel { ProfileViewModel(get()) }
    }
}