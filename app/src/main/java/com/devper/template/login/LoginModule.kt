package com.devper.template.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.devper.common.createCallService
import com.devper.template.common.url

val loginModule = module {
    single { createCallService<LoginService>(get(), url) }
    single { LoginRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
}