package com.devper.template.signup

import androidx.lifecycle.LiveData
import com.devper.common.api.Resource
import com.devper.common.toResource
import com.devper.template.common.AppDatabase
import com.devper.template.common.AppService
import com.devper.template.common.model.DataResponse
import com.devper.template.common.model.User
import com.devper.template.common.util.md5
import com.devper.template.signup.model.SignupRequest

class SignupRepository(val db: AppDatabase, private val service: AppService) {

    fun signup(request: SignupRequest): LiveData<Resource<DataResponse<User>>> {
        request.password = request.password?.md5()
        return service.signup(request).toResource()
    }

}