package com.devper.template.login

import androidx.lifecycle.LiveData
import com.devper.common.api.ApiResponse
import com.devper.common.api.NetworkBoundResource
import com.devper.common.api.Resource
import com.devper.common.toLiveData
import com.devper.template.common.AppDatabase
import com.devper.template.common.model.User
import com.devper.template.common.pref.AppPreference
import com.devper.template.common.util.ServiceHelper
import com.devper.template.common.util.md5
import com.devper.template.login.model.LoginRequest
import com.devper.template.login.model.LoginResponse

class LoginRepository(val db: AppDatabase, val service: LoginService) {

    private val pref = AppPreference.getInstance()

    fun login(username: String, password: String): LiveData<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, LoginResponse>() {
            override fun saveCallResult(item: LoginResponse) = db.user().addUser(item.data.user)

            override fun shouldFetch(data: List<User>?) = data == null || data.isEmpty()
            override fun loadFromDb() = db.user().getUser()
            override fun createCall(): LiveData<ApiResponse<LoginResponse>> {
                val request = LoginRequest(username, password.md5())
                return service.login(ServiceHelper.headers, request).toLiveData()
            }
        }.asLiveData()
    }

}