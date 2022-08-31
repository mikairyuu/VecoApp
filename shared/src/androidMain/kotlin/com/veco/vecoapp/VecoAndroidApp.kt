package com.veco.vecoapp

import android.app.Application
import android.content.Context

class VecoAndroidApp : Application() {

    init { INSTANCE = this }

    companion object {
        lateinit var INSTANCE: VecoAndroidApp
            private set

        val applicationContext: Context get() { return INSTANCE.applicationContext }
    }
}
