package com.devper.template.di

import com.devper.template.AppConfig
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.device.AndroidAppInfo
import com.devper.template.data.device.AndroidVersion
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.AppFactory
import com.devper.template.data.remote.AppInterceptor
import com.devper.template.data.remote.AppService
import com.devper.template.data.repository.MemberRepository
import com.devper.template.data.repository.DeviceRemoteRepository
import com.devper.template.data.repository.MovieRemoteRepository
import com.devper.template.data.repository.UserRemoteRepository
import com.devper.template.data.session.AppSession
import com.devper.template.domain.repository.DeviceRepository
import com.devper.template.domain.repository.MovieRepository
import com.devper.template.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single { AppPreference.init(get()) }
    single { AppDatabase.create(get(), false) }
    single { AppInterceptor(get()) }
    single { AppFactory.createOkHttpClient(get()) }
    single { AppFactory.createCallService<AppService>(get(), AppConfig.url) }
    single { AppSession() }

    factory { AndroidAppInfo(get()) }
    factory { AndroidVersion() }

    factory<DeviceRepository> { DeviceRemoteRepository(get(), get()) }
    factory<UserRepository> { UserRemoteRepository(get(), get(), get()) }
    factory<MovieRepository> { MovieRemoteRepository(get(), get()) }
    factory { MemberRepository(get()) }

}