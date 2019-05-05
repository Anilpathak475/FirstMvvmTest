package com.savedroid.wish

import android.app.Application
import android.content.Context

@Suppress("unused")
class WishApplication : Application() {

    init {
        INSTANCE = this
    }

    companion object {

        private var INSTANCE: WishApplication? = null

        fun applicationContext(): Context {
            return INSTANCE!!.applicationContext
        }

    }

}