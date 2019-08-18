package com.devper.template.signup

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.devper.common.createCallService
import com.devper.template.common.url

val signupModule = module {
    single { SignupRepository(get(), get()) }
    viewModel { SignupViewModel(get()) }
}