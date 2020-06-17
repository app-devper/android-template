package com.devper.template.data

import com.devper.template.domain.provider.AppInfoProvider
import com.devper.template.domain.repository.DeviceRepository
import com.devper.template.domain.repository.MovieRepository
import com.devper.template.domain.repository.UserRepository
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.device.AndroidAppInfo
import com.devper.template.data.remote.device.RegisterRepositoryImpl
import com.devper.template.data.remote.movie.MovieRepositoryImpl
import com.devper.template.data.remote.user.UserRepositoryImpl
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.RemoteFactory
import com.devper.template.data.remote.HttpInterceptor
import com.devper.template.data.remote.ApiService

import com.devper.template.data.session.AppSession

import org.koin.dsl.module

val dataModule = module {

    single { AppPreference.init(get()) }
    single { AppDatabase.create(get(), false) }
    single { HttpInterceptor(get()) }
    single { RemoteFactory.createOkHttpClient(get()) }
    single { RemoteFactory.createCallService<ApiService>(get(), "https://common-api-app.herokuapp.com/") }
    single { AppSession() }

    factory <AppInfoProvider> { AndroidAppInfo(get()) }

    factory<DeviceRepository> { RegisterRepositoryImpl(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }

}