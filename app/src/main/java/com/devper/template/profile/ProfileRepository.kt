package com.devper.template.profile

import androidx.lifecycle.LiveData
import com.devper.common.api.Resource
import com.devper.common.toResource
import com.devper.template.common.AppDatabase
import com.devper.template.common.AppService
import com.devper.template.common.model.DataResponse
import com.devper.template.common.model.User

class ProfileRepository(val db: AppDatabase, private val service: AppService) {

    fun getProfile(id: String): LiveData<Resource<DataResponse<User>>> {
        return service.getUserId(id).toResource()
    }

}