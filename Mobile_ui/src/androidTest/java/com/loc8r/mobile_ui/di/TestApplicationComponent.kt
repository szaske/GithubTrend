/**
 * This interface will be used by Dagger to build the required dependencies.
 * The @Component annotation allows me to add any Dagger modules I care to include
 * In this case I'm only adding AndroidInjectionModule, an internal Dagger component,
 * which is used by Dagger to provide DI to activities and fragments
 *
 */

package com.loc8r.mobile_ui.di

import android.app.Application
import com.loc8r.domain.interfaces.ProjectsRepository
import com.loc8r.mobile_ui.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * This is where the dagger magic happens.  Dagger2 requires an interface, I call it
 * TestApplicationComponent. The interface requires a function (inject) to tell Dagger where to
 * inject the dependencies.  Both of these names can be whatever I want, but xxxComponent and Inject
 * have become the standards used.
 *
 * Dagger2 steps to implement
 *
 * 1. Create injectable classes using the @Inject annotation
 * 2. Define the Dagger2 Magicbox interface (create xxComponent)
 * 3. Build the Dagger2 magicbox
 *
 *
 *
 * I'll need to mock applications, cache and Data so I add the TestApplicationModule,
 * TestCacheModuule, TestRemote and TestDataModule's.  I don't need to mock anything in the Presentation and
 * UiModules so no need to create Test versions of them.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        TestApplicationModule::class,
        TestCacheModule::class,
        TestDataModule::class,
        TestRemoteModule::class,
        PresentationModule::class,
        UiModule::class))
interface TestApplicationComponent {

    //I now need to provide a way for this component to be constructed
    @Component.Builder
    interface Builder {

        // BindsInstance gives me a way to bind the component to the application
        // meaning I can use the application itself to retrieve the component builder
        @BindsInstance
        fun application(application:Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)

    fun projectsRepository(): ProjectsRepository

}