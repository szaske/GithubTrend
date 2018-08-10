/**
 * A ViewModel factory to generate the ViewModel I'm asking for depending on the one being requested
 * This class extends the ViewModelProvider.Factory
 *
 * We need to use a custom view model factory class because we are injecting our view model
 * instances into our classes. We could use the ViewModelFactory that is provided by architecture
 * components, but we would need to define a ViewModelModule (similar to how we do for other
 * parts of the app), and having to update this for every ViewModel could get a tedious, our
 * custom factory gives us the ability to be flexible with our Injection for View Models.
 *
 * example Map:
 *  BrowseProjectsViewModel, BrowseProjectsViewModelModule
 *  BrowseBookmarkedProjectsViewModel, BrowseBookmarkedProjectsModule
 *
 *
 *
 */
package com.loc8r.mobile_ui.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
open class ViewModelFactory : ViewModelProvider.Factory {

    // This is a Map that correlates the ViewModel with the provider
    private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>

    @Inject constructor(creators: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>){
        this.creators = creators
    }

    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // Look in the Map for the provider according to the modelClass
        var creator: Provider<out ViewModel>? = creators[modelClass]

        // Checks to see if the ViewModel is a superclass of something in the map
        // and assumes that provider
        if(creator==null){
            for((key,value) in creators){
                if(modelClass.isAssignableFrom(key)){
                    creator = value
                }
            }
        }
        // If it's still not in the map I've done something wrong
        if(creator==null){
            throw IllegalStateException("Uknown model class: " + modelClass)
        }

        // Creator is in the map, so I'll call the Providers get method.
        try {

            return creator.get() as T
        } catch (e: Exception){
            throw RuntimeException(e)
        }
    }

}