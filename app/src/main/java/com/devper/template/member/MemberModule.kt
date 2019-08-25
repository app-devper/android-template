package com.devper.template.member

import com.devper.template.member.data.MemberRepository
import com.devper.template.member.ui.MemberFragment
import com.devper.template.member.ui.MemberViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val memberModule = module {
    factory { MemberRepository(get(), get()) }

    scope(named<MemberFragment>()) {
        viewModel { MemberViewModel(get()) }
    }

}