package com.devper.template.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.template.common.model.User

class LoginViewModel internal constructor(private val repo: LoginRepository) : ViewModel() {

    var username: ObservableField<String>? = null
    var password: ObservableField<String>? = null

    var results: LiveData<Resource<List<User>>> = MutableLiveData()
    var login: MutableLiveData<String> = MutableLiveData()

    init {
        username = ObservableField("wowit")
        password = ObservableField("password")
        results = Transformations.switchMap(login) {
            when (it) {
                "Login" -> repo.login(username?.get()!!, password?.get()!!)
                else -> AbsentLiveData.create()
            }
        }
    }

    fun login() {
        if (username?.get().isNullOrEmpty() || password?.get().isNullOrEmpty()) return
        login.value = "Login"
    }

    fun fbLogin() {
        login.value = "Facebook"
    }

    fun lineLogin() {
        login.value = "Line"
    }

    fun googleLogin() {
        login.value = "Google"
    }
}