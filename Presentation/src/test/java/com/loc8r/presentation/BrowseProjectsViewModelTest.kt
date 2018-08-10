package com.loc8r.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.loc8r.domain.interactors.BookmarkProject
import com.loc8r.domain.interactors.GetProjects
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
class BrowseProjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // First we create mock for the dependencies for the view model
    var getProjects = mock<GetProjects>()
    var bookmarkProject = mock<BookmarkProject>()
    var unbookmarkProjectTest = mock<UnbookmarkProject>()
    var projectMapper = mock<ProjectViewMapper>()
    // Then we use those mocks to instantiate the view model
    var projectViewModel =  BrowseProjectsViewModel(getProjects,bookmarkProject,unbookmarkProjectTest,projectMapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase(){
        // projectViewModel.fetchProjects()
        verify(getProjects, times(1)).execute(any(),eq(null))
    }
}