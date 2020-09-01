package com.devper.template.presentation.pin.viewmodel

import com.devper.template.R
import com.devper.template.presentation.BaseViewModel

class PinMaxAttemptViewModel : BaseViewModel() {

    fun nextPage() {
        onNavigate(R.id.pin_max_to_forgot_pin, null)
    }
}