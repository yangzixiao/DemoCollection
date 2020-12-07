package com.yzx.democollection.ui.livedata

import androidx.lifecycle.MutableLiveData
import com.kunminx.architecture.ui.callback.UnPeekLiveData

object LiveDataInstance {

    val unPeekLiveData=UnPeekLiveData<String>()
    val liveData = MutableLiveData<String>()
}