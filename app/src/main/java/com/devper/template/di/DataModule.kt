package com.devper.template.di

import com.devper.template.data.remote.auth.AuthRepository
import com.devper.template.data.remote.auth.AuthRepositoryImpl
import com.devper.template.data.remote.device.DeviceRepository
import com.devper.template.data.remote.device.RegisterRepositoryImpl
import com.devper.template.data.remote.notification.NotificationRepository
import com.devper.template.data.remote.notification.NotificationRepositoryImpl
import com.devper.template.data.remote.otp.OtpRepository
import com.devper.template.data.remote.otp.OtpRepositoryImpl
import com.devper.template.data.remote.termcondition.TermConditionRepository
import com.devper.template.data.remote.termcondition.TermConditionRepositoryImpl
import com.devper.template.data.remote.user.UserRepository
import com.devper.template.data.remote.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDeviceRepository(repositoryImpl: RegisterRepositoryImpl): DeviceRepository

    @Binds
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindOtpRepository(repositoryImpl: OtpRepositoryImpl): OtpRepository

    @Binds
    abstract fun bindNotificationRepository(repositoryImpl: NotificationRepositoryImpl): NotificationRepository

    @Binds
    abstract fun bindTermConditionRepository(repositoryImpl: TermConditionRepositoryImpl): TermConditionRepository

}
