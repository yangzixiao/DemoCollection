package com.yzx.democollection.manager

import android.app.Application
import java.lang.IllegalStateException


object AppManager {

    private var application: Application? = null

    fun init(application: Application) {
        AppManager.application = application
    }


    fun getApplication(): Application {
        if (application == null) {
            throw IllegalStateException("not init AppManager")
        }
        return application!!
    }
}