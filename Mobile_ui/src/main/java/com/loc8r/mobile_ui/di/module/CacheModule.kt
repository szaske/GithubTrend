package com.loc8r.mobile_ui.di.module

import android.app.Application
import com.loc8r.cache.ProjectsCacheImpl
import com.loc8r.cache.ProjectsDatabase
import com.loc8r.data.interfaces.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideDataBase(application: Application): ProjectsDatabase {
            return ProjectsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}