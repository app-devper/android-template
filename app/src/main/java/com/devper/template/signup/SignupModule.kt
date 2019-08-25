package com.devper.template.signup

import com.devper.template.signup.data.SignupRepository
import com.devper.template.signup.ui.SignupFragment
import com.devper.template.signup.ui.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val signupModule = module {
    factory { SignupRepository(get(), get()) }
    scope(named<SignupFragment>()) {
        viewModel { SignupViewModel(get()) }
    }
}