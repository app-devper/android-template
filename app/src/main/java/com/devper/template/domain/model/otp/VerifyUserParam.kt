package com.devper.template.domain.model.otp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyUserParam(
    val userRefId: String,
    val channel: String
) : Parcelable