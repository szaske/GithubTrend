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

    @Provides
    @JvmStatic
    fun provideDataBase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    /**
     * I'm going to want to return a mock for the cache in case it's accessed by the application test
     * and I want to mock a response
     */
    @Provides
    @JvmStatic
    fun provideProjectsCache(): ProjectsCache{
        return mock()
    }
}