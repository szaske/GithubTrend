package com.loc8r.mobile_ui

import android.app.Application
import timber.log.Timber

class GithubTrendApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber(){
        Timber.plant(Timber.DebugTree())
    }

}