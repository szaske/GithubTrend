package com.loc8r.mobile_ui.di.module

import com.loc8r.cache.BuildConfig
import com.loc8r.data.interfaces.ProjectsRemote
import com.loc8r.remote.GithubTrendServiceFactory
import com.loc8r.remote.ProjectsRemoteImpl
import com.loc8r.remote.interfaces.GithubTrendService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendService {
            return GithubTrendServiceFactory.makeGithubTrendService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}
