package com.devper.template.common

import com.devper.template.MainViewModel
import com.devper.template.common.pref.AppPreference
import com.devper.template.login.loginModule
import com.devper.template.member.memberModule
import com.devper.template.movie.movieModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppPreference.init(get()) }
    single { AppDatabase.create(get(), false) }
    single { createOkHttpClient() }

    // MainViewModel ViewModel
    viewModel { MainViewModel() }

}

val appModules = listOf(appModule, loginModule, memberModule, movieModule)