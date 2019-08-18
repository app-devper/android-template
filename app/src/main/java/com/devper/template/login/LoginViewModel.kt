package com.devper.template.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.smartlogin.LoginType
import com.devper.template.common.model.DataResponse
import com.devper.template.login.model.Login

class LoginViewModel internal constructor(private val repo: LoginRepository) : ViewModel() {

    var username: ObservableField<String>? = null
    var password: ObservableField<String>? = null

    var results: LiveData<Resource<DataResponse<Login>>> = MutableLiveData()
    var login: MutableLiveData<LoginType> = MutableLiveData()

    init {
        username = ObservableField("wowit")
        password = ObservableField("password")
        results = Transformations.switchMap(login) {
            when (it) {
                LoginType.Custom -> repo.login(username!!.get()!!, password!!.get()!!)
                else -> AbsentLiveData.create()
            }
        }
    }

    fun login() {
        if (username?.get().isNullOrEmpty() || password?.get().isNullOrEmpty()) return
        login.value = LoginType.Custom
    }

    fun fbLogin() {
        login.value = LoginType.Facebook
    }

    fun lineLogin() {
        login.value = LoginType.Line
    }

    fun googleLogin() {
        login.value = LoginType.Google
    }
}