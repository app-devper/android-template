package com.devper.template.di

import android.app.Activity
import com.devper.template.core.widget.ProgressHudDialog
import com.devper.template.presentation.login.viewmodel.LoginViewModel
import com.devper.template.presentation.main.MainActivity
import com.devper.template.presentation.main.viewmodel.MainViewModel
import com.devper.template.presentation.movie.viewmodel.MovieDetailViewModel
import com.devper.template.presentation.movie.viewmodel.MovieViewModel
import com.devper.template.presentation.profile.viewmodel.ProfileViewModel
import com.devper.template.presentation.signup.viewmodel.SignupViewModel
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
