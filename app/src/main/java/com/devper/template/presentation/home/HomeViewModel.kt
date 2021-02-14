package com.devper.template.presentation.home

import androidx.lifecycle.viewModelScope
import com.devper.template.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {

    fun initPin() {
        viewModelScope.launch {

        }
    }

}