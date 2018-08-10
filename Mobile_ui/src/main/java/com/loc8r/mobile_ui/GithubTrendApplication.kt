package com.loc8r.mobile_ui

import android.app.Activity
import android.app.Application
import com.loc8r.mobile_ui.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class GithubTrendApplication: Application(), HasActivityInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    /** Returns an [AndroidInjector] of [Activity]s.  */
    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()

        // application() binds these components to the Application and return our builder
        //
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

    }

    private fun setupTimber(){
        Timber.plant(Timber.DebugTree())
    }

}