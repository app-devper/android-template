package com.devper.template.di

import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.domain.usecase.movie.GetConfigUseCase
import com.devper.template.domain.usecase.movie.GetMovieUseCase
import com.devper.template.domain.usecase.movie.GetMoviesUseCase
import com.devper.template.domain.usecase.user.*
import org.koin.dsl.module

private val domainModule = module {

    factory { RegisterDeviceUseCase(get()) }

    factory { LoginUseCase(get()) }
    factory { SignupUseCase(get()) }
    factory { GetProfileUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { ClearUserUseCase(get()) }

    factory { GetConfigUseCase(get()) }
    factory { GetMovieUseCase(get()) }
    factory { GetMoviesUseCase(get()) }
}

val domainsModule  = listOf(dataModule, domainModule)
