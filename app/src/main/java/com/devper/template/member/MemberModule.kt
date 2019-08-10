package com.devper.template.member

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.devper.common.createCallService
import com.devper.template.common.url

val memberModule = module {
    single { createCallService<MemberService>(get(), url) }
    single { MemberRepository(get(), get()) }
    viewModel { MemberViewModel(get()) }
}