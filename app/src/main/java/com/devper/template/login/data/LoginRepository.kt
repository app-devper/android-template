package com.devper.template.login.data

import androidx.lifecycle.LiveData
import com.devper.common.api.Resource
import com.devper.common.toResource
import com.devper.template.app.db.AppDatabase
import com.devper.template.app.api.AppService
import com.devper.template.app.ext.md5
import com.devper.template.app.model.DataResponse
import com.devper.template.app.pref.AppPreference
import com.devper.template.login.model.Login
import com.devper.template.login.model.LoginRequest

class LoginRepository(val db: AppDatabase, private val service: AppService) {

    private val pref = AppPreference.getInstance()

    fun login(username: String, password: String): LiveData<Resource<DataResponse<Login>>> {
        val request = LoginRequest(username, password.md5(), "app", pref.deviceUuid, "android", pref.fbToken)
        return service.login(request).toResource()
    }

}