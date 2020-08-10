package com.devper.template.di

import com.devper.template.data.remote.auth.AuthRepositoryImpl
import com.devper.template.data.remote.device.RegisterRepositoryImpl
import com.devper.template.data.remote.otp.OtpRepositoryImpl
import com.devper.template.data.remote.user.UserRepositoryImpl
import com.devper.template.domain.repository.AuthRepository
import com.devper.template.domain.repository.DeviceRepository
import com.devper.template.domain.repository.OtpRepository
import com.devper.template.domain.repository.UserRepository
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

}
