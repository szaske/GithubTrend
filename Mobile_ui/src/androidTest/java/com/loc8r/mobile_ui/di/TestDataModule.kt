package com.loc8r.mobile_ui.di

import com.loc8r.domain.interfaces.ProjectsRepository
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    /**
     * I'm going to want to return a mock for the cache in case it's accessed by the application test
     * and I want to mock a response
     */
    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): ProjectsRepository{
        return mock()
    }
}