package com.devper.template.data.preference

interface PreferenceStorage {
    var userId: String
    var pin: String
    fun clear()
}