package com.devper.template.di

import com.devper.template.domain.repository.AuthRepository
import com.devper.template.data.remote.auth.AuthRepositoryImpl
import com.devper.template.domain.repository.DeviceRepository
import com.devper.template.data.remote.device.RegisterRepositoryImpl
import com.devper.template.domain.repository.NotificationRepository
import com.devper.template.data.remote.notification.NotificationRepositoryImpl
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.data.remote.otp.OtpRepositoryImpl
import com.devper.template.domain.repository.TermConditionRepository
import com.devper.template.data.remote.termcondition.TermConditionRepositoryImpl
import com.devper.template.domain.repository.UserRepository
import com.devper.template.data.remote.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
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
