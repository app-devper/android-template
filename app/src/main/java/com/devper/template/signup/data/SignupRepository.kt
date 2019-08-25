package com.devper.template.signup.data

import androidx.lifecycle.LiveData
import com.devper.common.api.Resource
import com.devper.common.toResource
import com.devper.template.app.db.AppDatabase
import com.devper.template.app.api.AppService
import com.devper.template.app.ext.md5
import com.devper.template.app.model.DataResponse
import com.devper.template.app.model.User
import com.devper.template.signup.model.SignupRequest

class SignupRepository(val db: AppDatabase, private val service: AppService) {

    fun signup(request: SignupRequest): LiveData<Resource<DataResponse<User>>> {
        request.password = request.password?.md5()
        return service.signup(request).toResource()
    }

}