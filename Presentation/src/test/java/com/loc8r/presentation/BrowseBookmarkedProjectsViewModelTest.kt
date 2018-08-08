package com.loc8r.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.loc8r.domain.interactors.BookmarkProject
import com.loc8r.domain.interactors.GetBookmarkedProjects
import com.loc8r.domain.interactors.UnbookmarkProject
import com.loc8r.domain.models.Project
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class BrowseBookmarkedProjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // First we create mock for the dependencies for the view model
    var getBookmarkedProjects = mock<GetBookmarkedProjects>()
    //var bookmarkProject = mock<BookmarkProject>()
    //var unbookmarkProjectTest = mock<UnbookmarkProject>()
    var mapper = mock<ProjectViewMapper>()
    // Then we use those mocks to instantiate the view model
    var projectViewModel =  BrowseBookmarkedProjectsViewModel(getBookmarkedProjects,mapper)

    // This is class that us used to capture the events that were returned from subscriptions, so
    // I can manipulate the responses that are returned by them
    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase(){
        // projectViewModel.fetchProjects()
        verify(getBookmarkedProjects, times(1)).execute(any(), eq(null))
    }
}