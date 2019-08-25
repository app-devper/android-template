package com.devper.template

import android.app.Activity
import com.devper.template.app.widget.ProgressHudDialog
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {

    scope(named<MainActivity>()) {
        scoped { (activity: Activity) ->
            ProgressHudDialog.init(activity, "Loading...", false)
        }
        viewModel { MainViewModel() }
    }

}
