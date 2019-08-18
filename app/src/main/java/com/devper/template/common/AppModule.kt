package com.devper.template.common

import android.app.Activity
import com.devper.common.createCallService
import com.devper.template.MainViewModel
import com.devper.template.MainActivity
import com.devper.template.common.pref.AppPreference
import com.devper.template.common.util.RequestInterceptor
import com.devper.template.login.loginModule
import com.devper.template.member.memberModule
import com.devper.template.movie.movieModule
import com.devper.template.profile.profileModule
import com.devper.template.signup.signupModule
import com.devper.template.widget.ProgressHudDialog
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { AppPreference.init(get()) }
    single { AppDatabase.create(get(), false) }
    single { RequestInterceptor(get()) }
    single { createOkHttpClient(get()) }
    single { createCallService<AppService>(get(), url) }

    scope(named<MainActivity>()) {
        scoped { (activity: Activity) ->
            ProgressHudDialog.init(activity, "Loading...", false)
        }
    }

    // MainViewModel ViewModel
    viewModel { MainViewModel() }

}

val appModules = listOf(
    appModule,
    loginModule, memberModule,
    movieModule, signupModule, profileModule
)