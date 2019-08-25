package com.devper.template.login

import com.devper.template.login.data.LoginRepository
import com.devper.template.login.ui.LoginFragment
import com.devper.template.login.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loginModule = module {
    factory { LoginRepository(get(), get()) }
    scope(named<LoginFragment>()) {
        viewModel { LoginViewModel(get()) }
    }
}