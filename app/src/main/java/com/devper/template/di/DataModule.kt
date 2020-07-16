package com.devper.template.di

import com.devper.template.core.platform.helper.NetworkInfoHelper
import com.devper.template.domain.provider.AppInfoProvider
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.device.AndroidAppInfo
import com.devper.template.data.remote.device.RegisterRepositoryImpl
import com.devper.template.data.remote.movie.MovieRepositoryImpl
import com.devper.template.data.remote.user.UserRepositoryImpl
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.RemoteFactory
import com.devper.template.data.remote.HttpInterceptor
import com.devper.template.data.remote.ApiService
import com.devper.template.data.remote.auth.AuthRepositoryImpl
import com.devper.template.data.remote.otp.OtpRepositoryImpl

import com.devper.template.data.session.AppSession
import com.devper.template.domain.repository.*

import org.koin.dsl.module

val dataModule = module {

    single { AppSession() }
    single { AppPreference(get()) }
    single { AppDatabase.create(get(), false) }
    single { NetworkInfoHelper(get()) }
    single { HttpInterceptor(get(), get()) }
    single { RemoteFactory(get()) }
    single { get<RemoteFactory>().createOkHttpClient(get()) }
    single<ApiService> {
        get<RemoteFactory>().createApiService(get(), "https://common-api-app.herokuapp.com/")
    }

    factory<AppInfoProvider> { AndroidAppInfo(get()) }

    factory<DeviceRepository> { RegisterRepositoryImpl(get(), get(), get()) }
    factory<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    factory<OtpRepository> { OtpRepositoryImpl(get()) }

}