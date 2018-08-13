package com.loc8r.mobile_ui.di

import com.loc8r.data.interfaces.ProjectsRemote
import com.loc8r.remote.interfaces.GithubTrendService
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideGithubService(): GithubTrendService {
        return mock()
    }

    @Provides
    @JvmStatic
    fun provideProjectsRemote(): ProjectsRemote{
        return mock()
    }
}