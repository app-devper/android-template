package com.devper.template.profile

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    single { ProfileRepository(get(), get()) }
    viewModel { ProfileViewModel(get()) }
}