package com.devper.template.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.template.common.model.DataResponse
import com.devper.template.common.model.User

class ProfileViewModel internal constructor(private val repo: ProfileRepository) : ViewModel() {

    var userId: MutableLiveData<String> = MutableLiveData()
    var results: LiveData<Resource<DataResponse<User>>> = Transformations.switchMap(userId) {
        when (it) {
            null -> AbsentLiveData.create()
            else -> repo.getProfile(it)
        }
    }

    fun getProfile(id: String) {
        userId.postValue(id)
    }

}