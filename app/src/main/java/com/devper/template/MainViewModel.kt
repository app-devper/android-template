package com.devper.template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    var logout: MutableLiveData<String> = MutableLiveData()

    fun logout() {
        logout.value = "logout"
    }
}
