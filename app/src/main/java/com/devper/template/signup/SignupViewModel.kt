package com.devper.template.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.devper.common.api.AbsentLiveData
import com.devper.common.api.Resource
import com.devper.template.common.model.BaseResponse
import com.devper.template.common.model.DataResponse
import com.devper.template.common.model.User
import com.devper.template.signup.model.SignupRequest

class SignupViewModel internal constructor(private val repo: SignupRepository) : ViewModel() {

    var user: SignupRequest = SignupRequest()

    var signup: MutableLiveData<String> = MutableLiveData()
    var results: LiveData<Resource<DataResponse<User>>> = Transformations.switchMap(signup) {
        when (it) {
            "Signup" -> repo.signup(user)
            else -> AbsentLiveData.create()
        }
    }


    fun signup() {
        Log.i("Signup", "Signup: $user")
        if (user.username == null || user.password == null) {
            return
        }
        signup.postValue("Signup")
    }

}