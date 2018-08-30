/**
 * This Test Cache module will be used within our tests
 */

package com.loc8r.mobile_ui.di

import android.app.Application
import com.loc8r.cache.ProjectsCacheImpl
import com.loc8r.cache.ProjectsDatabase
import com.loc8r.data.interfaces.ProjectsCache
import com.nhaarman.mockito_kotlin.mock
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

    /**
     * The database doesn't really matter to much, so even in our tests I'm going to return the
     * actual local database
     */
    @Provides
    @JvmStatic
    fun provideDataBase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    /**
     * I'm going to want to return a mock for the cache in case it's accessed by the application test
     * and I want to mock a response.  So in this case, when a test requests the cache I'll return a
     * Mockito mock.
     */
    @Provides
    @JvmStatic
    fun provideProjectsCache(): ProjectsCache{
        return mock()
    }
}