/**
 * This interface will be used by Dagger to build the required dependencies.
 * The @Component annotation allows me to add any Dagger modules I care to include
 * In this case I'm only adding AndroidInjectionModule, an internal Dagger component,
 * which is used by Dagger to provide DI to activities and fragments
 *
 */

package com.loc8r.mobile_ui.di

import android.app.Application
import com.loc8r.mobile_ui.GithubTrendApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class))
interface ApplicationComponent {

    //I now need to provide a way for this component to be constructed
    @Component.Builder
    interface Builder {

        // BindsInstance gives me a way to bind the component to the application
        // meaning I can use the application itself to retrieve the component builder
        @BindsInstance
        fun application(application:Application): Builder

        fun build(): ApplicationComponent
    }

    // This function will take our application and inject the dependencies I define
    fun inject(app: GithubTrendApplication)

}