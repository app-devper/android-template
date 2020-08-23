package com.devper.template.di

import android.content.Context
import com.devper.template.data.remote.NetworkInfoHelper
import com.devper.template.core.platform.session.CountDownSession
import com.devper.template.core.thread.CoroutinesDispatcher
import com.devper.template.data.database.AppDatabase
import com.devper.template.data.device.AndroidAppInfo
import com.devper.template.data.preference.AppPreference
import com.devper.template.data.remote.ApiService
import com.devper.template.data.remote.HttpInterceptor
import com.devper.template.data.remote.RemoteFactory
import com.devper.template.data.session.AppSession
import com.devper.template.data.session.AppSessionProvider
import com.devper.template.domain.core.thread.Dispatcher
import com.devper.template.domain.provider.AppInfoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppSession(): AppSessionProvider = AppSession()

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): AppPreference = AppPreference(context)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.create(context, false)

    @Singleton
    @Provides
    fun provideAppInfoProvider(@ApplicationContext context: Context): AppInfoProvider = AndroidAppInfo(context)

    @Singleton
    @Provides
    fun provideNetworkInfo(@ApplicationContext context: Context): NetworkInfoHelper =
        NetworkInfoHelper(context)

    @Singleton
    @Provides
    fun provideRemoteFactory(@ApplicationContext context: Context): RemoteFactory = RemoteFactory(context)

    @Singleton
    @Provides
    fun provideApiService(remoteFactory: RemoteFactory, interceptor: HttpInterceptor): ApiService {
        val okHttp = remoteFactory.createOkHttpClient(interceptor)
        return remoteFactory.createApiService(okHttp, "https://common-api-app.herokuapp.com/")
    }

    @Singleton
    @Provides
    fun provideHttpInterceptor(session: AppSessionProvider, networkInfoHelper: NetworkInfoHelper): HttpInterceptor = HttpInterceptor(session, networkInfoHelper)

    @Singleton
    @Provides
    fun provideDispatcher(): Dispatcher = CoroutinesDispatcher()

    @Singleton
    @Provides
    fun provideCountDownSession(): CountDownSession = CountDownSession(600 * 1000L)

}
