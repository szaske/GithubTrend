package com.loc8r.mobile_ui.di.module

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.mobile_ui.BookmarkedActivity
import com.loc8r.mobile_ui.BrowseActivity
import com.loc8r.mobile_ui.UiThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity
}