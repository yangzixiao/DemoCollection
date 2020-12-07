package com.yzx.democollection.ui.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveDataViewModel : ViewModel() {

    val stringLiveData = MutableLiveData<String>()
}