package com.devper.template.di

import android.app.Activity
import com.devper.template.core.widget.ProgressHudDialog
import com.devper.template.presentation.login.LoginViewModel
import com.devper.template.presentation.main.MainActivity
import com.devper.template.presentation.main.MainViewModel
import com.devper.template.presentation.movie.MovieDetailViewModel
import com.devper.template.presentation.movie.MovieViewModel
import com.devper.template.presentation.profile.ProfileViewModel
import com.devper.template.presentation.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    scope(named<MainActivity>()) {
        scoped { (activity: Activity) ->
            ProgressHudDialog.init(activity, "Loading...", false)
        }
    }

    viewModel { MainViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MovieViewModel(get(), get()) }
    viewModel { MovieDetailViewModel(get()) }

}
