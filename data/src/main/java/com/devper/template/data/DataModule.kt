package com.devper.template.data

import com.devper.template.core.model.application.AppInfo
import com.devper.template.core.repository.DeviceRepository
import com.devper.template.core.repository.MovieRepository
import com.devper.template.core.repository.UserRepository
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.device.AndroidAppInfo
import com.devper.template.data.gateway.DeviceRepositoryImpl
import com.devper.template.data.gateway.MovieRepositoryImpl
import com.devper.template.data.gateway.UserRepositoryImpl
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

    factory <AppInfo> { AndroidAppInfo(get()) }

    factory<DeviceRepository> { DeviceRepositoryImpl(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }

}