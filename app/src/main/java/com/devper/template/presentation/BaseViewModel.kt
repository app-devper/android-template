package com.devper.template.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    private val _flow = MutableLiveData<String>()
    var flow: String?
        get() = _flow.value
        set(value) {
            _flow.value = value
        }
    var onNavigate: (id: Int, bundle: Bundle?) -> Unit = { _, _ -> }
    var retry:() -> Unit = {}

     var isLoading = MutableLiveData(View.GONE)
}