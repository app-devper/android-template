package com.devper.template.profile.data

import androidx.lifecycle.LiveData
import com.devper.common.api.Resource
import com.devper.common.toResource
import com.devper.template.app.db.AppDatabase
import com.devper.template.app.api.AppService
import com.devper.template.app.model.DataResponse
import com.devper.template.app.model.User

class ProfileRepository(val db: AppDatabase, private val service: AppService) {

    fun getProfile(id: String): LiveData<Resource<DataResponse<User>>> {
        return service.getUserId(id).toResource()
    }

}