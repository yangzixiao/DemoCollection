package com.yzx.democollection

import android.app.Application
import com.yzx.democollection.manager.AppManager

class DemoApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        AppManager.init(this)
    }
}