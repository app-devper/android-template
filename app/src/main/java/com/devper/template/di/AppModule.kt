package com.devper.template.di

import android.app.Activity
import com.devper.template.core.platform.widget.ProgressHudDialog
import com.devper.template.core.thread.CoroutinesDispatcher
import com.devper.template.domain.core.thread.CoroutineThreadDispatcher
import com.devper.template.presentation.BaseViewModel
import com.devper.template.presentation.login.LoginPinViewModel
import com.devper.template.presentation.login.LoginViewModel
import com.devper.template.presentation.main.MainActivity
import com.devper.template.presentation.main.MainViewModel
import com.devper.template.presentation.movie.MovieDetailViewModel
import com.devper.template.presentation.movie.MovieViewModel
import com.devper.template.presentation.otp.OtpChannelViewModel
import com.devper.template.presentation.otp.OtpVerifyViewModel
import com.devper.template.presentation.password.PasswordChangeViewModel
import com.devper.template.presentation.password.PasswordViewModel
import com.devper.template.presentation.pin.PinChangeViewModel
import com.devper.template.presentation.pin.PinFormViewModel
import com.devper.template.presentation.profile.ProfileViewModel
import com.devper.template.presentation.setting.SettingViewModel
import com.devper.template.presentation.signup.SignUpViewModel
import com.devper.template.presentation.splash.SplashViewModel
import com.devper.template.presentation.user.UserDetailViewModel
import com.devper.template.presentation.user.UserFormViewModel
import com.devper.template.presentation.user.UserViewModel
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
    viewModel { PasswordChangeViewModel(get(), get()) }
    viewModel { PinChangeViewModel(get()) }
    viewModel { SettingViewModel() }
    viewModel { UserViewModel(get()) }
    viewModel { UserDetailViewModel(get()) }
    viewModel { UserFormViewModel(get(), get()) }

}
