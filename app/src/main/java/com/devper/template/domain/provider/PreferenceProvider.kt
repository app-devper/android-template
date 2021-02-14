package com.devper.template.domain.provider

interface PreferenceProvider {
    var userId: String
    var pin: String
    fun clear()
}