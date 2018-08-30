package com.loc8r.mobile_ui.di

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication: Application(), HasActivityInjector {

    @Inject lateinit var injector: DispatchingAndroidInjector<Activity>
    private lateinit var appComponent: TestApplicationComponent

    /**
     * During testing I'm going to want to retrieve the TestApplicationComponent, so this companion
     * object and functions gives me a way to retrieve it
     */
    companion object {
        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext
                    as TestApplication).appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)
    }

    /** Returns an [AndroidInjector] of [Activity]s.  */
    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }
}