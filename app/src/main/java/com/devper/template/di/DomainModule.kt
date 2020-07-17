package com.devper.template.di

import com.devper.template.domain.usecase.auth.*
import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.domain.usecase.movie.GetConfigUseCase
import com.devper.template.domain.usecase.movie.GetMovieUseCase
import com.devper.template.domain.usecase.movie.GetMoviesUseCase
import com.devper.template.domain.usecase.otp.GetChannelUseCase
import com.devper.template.domain.usecase.otp.VerifyCodeUseCase
import com.devper.template.domain.usecase.otp.VerifyUserUseCase
import com.devper.template.domain.usecase.user.*
import org.koin.dsl.module

private val domainModule = module {

    factory { RegisterDeviceUseCase(get(), get()) }

    factory { LoginUseCase(get(), get()) }
    factory { SetPinUseCase(get(), get()) }
    factory { LoginPinUseCase(get(), get()) }
    factory { SetPasswordUseCase(get(), get()) }
    factory { VerifyPasswordUseCase(get(), get()) }
    factory { VerifyPinUseCase(get(), get()) }

    factory { SignUpUseCase(get(), get()) }
    factory { GetProfileUseCase(get(), get()) }
    factory { GetCurrentUserUseCase(get(), get()) }
    factory { ClearUserUseCase(get(), get()) }
    factory { GetUsersUseCase(get(), get()) }
    factory { GetUserUseCase(get(), get()) }

    factory { GetConfigUseCase(get(), get()) }
    factory { GetMovieUseCase(get(), get()) }
    factory { GetMoviesUseCase(get(), get()) }

    factory { GetChannelUseCase(get(), get()) }
    factory { VerifyUserUseCase(get(), get()) }
    factory { VerifyCodeUseCase(get(), get()) }


}

val domainsModule = listOf(dataModule, domainModule)
