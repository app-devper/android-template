package com.devper.template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    var logout: MutableLiveData<String> = MutableLiveData()
    var badge: MutableLiveData<String> = MutableLiveData()

    fun logout() {
        logout.value = "logout"
    }
}
