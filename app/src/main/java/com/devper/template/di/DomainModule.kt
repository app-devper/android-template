package com.devper.template.di

import com.devper.template.domain.usecase.device.RegisterDeviceUseCase
import com.devper.template.domain.usecase.movie.GetConfigUseCase
import com.devper.template.domain.usecase.movie.GetMovieUseCase
import com.devper.template.domain.usecase.movie.GetMoviesUseCase
import com.devper.template.domain.usecase.user.*
import org.koin.dsl.module

val domainModule = module {

    factory { RegisterDeviceUseCase(get()) }

    factory { LoginUseCase(get()) }
    factory { SignupUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { ClearUserUseCase(get()) }

    factory { GetConfigUseCase(get()) }
    factory { GetMovieUseCase(get()) }
    factory { GetMoviesUseCase(get()) }
}
