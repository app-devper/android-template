package com.devper.template.app

import com.devper.common.createCallService
import com.devper.template.app.AppConfig.MOVIE_URL
import com.devper.template.app.AppConfig.url
import com.devper.template.app.api.AppFactory.createOkHttpClient
import com.devper.template.app.api.AppInterceptor
import com.devper.template.app.api.AppService
import com.devper.template.app.api.MovieService
import com.devper.template.app.db.AppDatabase
import com.devper.template.app.pref.AppPreference
import org.koin.dsl.module

val appModule = module {

    single { AppPreference.init(get()) }
    single { AppDatabase.create(get(), false) }
    single { AppInterceptor(get()) }
    single { createOkHttpClient(get()) }
    single { createCallService<AppService>(get(), url) }
    single { createCallService<MovieService>(get(), MOVIE_URL) }

}
