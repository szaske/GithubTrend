/**
 * The Presentation dagger module is a bit more complex as I need to handle injections of the viewmodel
 * classes
 */
package com.loc8r.mobile_ui.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.loc8r.mobile_ui.di.ViewModelFactory
import com.loc8r.presentation.BrowseProjectsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

    // This is the function that will be used to inject a BrowseProjectsViewModel
    @Binds
    @IntoMap
    @ViewModelKey(BrowseProjectsViewModel::class)
    abstract fun bindBrowseProjectsViewModel(viewModel: BrowseProjectsViewModel): ViewModel

    // Because I want to use my own View model factory instead of the JetPack one, I need to inject
    // an instance of this wherever a viewModel is needed
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


}

// Here I create my own custom annotation ViewModelKey
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)