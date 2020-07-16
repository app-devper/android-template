package com.devper.template.di

import android.app.Activity
import com.devper.template.core.thread.CoroutinesDispatcher
import com.devper.template.core.platform.widget.ProgressHudDialog
import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.presentation.BaseViewModel
import com.devper.template.presentation.login.LoginViewModel
import com.devper.template.presentation.main.MainActivity
import com.devper.template.presentation.main.MainViewModel
import com.devper.template.presentation.moviedetail.MovieDetailViewModel
import com.devper.template.presentation.movie.MovieViewModel
import com.devper.template.presentation.otpchannel.OtpChannelViewModel
import com.devper.template.presentation.otpverify.OtpVerifyViewModel
import com.devper.template.presentation.loginpin.LoginPinViewModel
import com.devper.template.presentation.pinform.PinFormViewModel
import com.devper.template.presentation.profile.ProfileViewModel
import com.devper.template.presentation.password.PasswordViewModel
import com.devper.template.presentation.passwordchange.PasswordChangeViewModel
import com.devper.template.presentation.pinchange.PinChangeViewModel
import com.devper.template.presentation.setting.SettingViewModel
import com.devper.template.presentation.signup.SignUpViewModel
import com.devper.template.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single<CoroutineThreadDispatcher> { CoroutinesDispatcher() }

    scope(named<MainActivity>()) {
        scoped { (activity: Activity) ->
            ProgressHudDialog.init(activity, "Loading...", false)
        }
    }

    viewModel { MainViewModel(get()) }
    viewModel { BaseViewModel() }
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ProfileViewModel() }
    viewModel { MovieViewModel(get(), get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { OtpChannelViewModel(get()) }
    viewModel { OtpVerifyViewModel(get(), get()) }
    viewModel { PinFormViewModel(get()) }
    viewModel { LoginPinViewModel(get()) }
    viewModel { PasswordViewModel(get()) }
    viewModel { PasswordChangeViewModel(get()) }
    viewModel { PinChangeViewModel(get()) }
    viewModel { SettingViewModel() }

}
