package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.models.Project
import com.loc8r.domain.interfaces.ProjectsRepository
import com.loc8r.domain.interactors.factory.ProjectDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest {

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    // Because our tests will be interacting with the projectsRepository
    // we'll need to create a mock observable.  This method creates
    // a mock Repository that returns the given observable
    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getBookmarkedProjects())
                .thenReturn(observable)
    }

    @Before
    fun setup(){
        // This creates mocks for the projectRepo and postExecutionThread
        MockitoAnnotations.initMocks(this)

        // this creates our GetBookmarkedProjects class, which gets us a list of bookmarked Projects
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository,postExecutionThread)
    }

    // This tests to see that the getProjects call to the
    // The just() method emits its parameter(s) as OnNext notifications,
    // and after that, it emits an OnCompleted notification.
    // see: https://medium.com/@vanniktech/testing-rxjava-code-made-easy-4cc32450fc9a
    @Test
    fun getBookmarkedProjectsCompletes(){
        // This creates an mock repo
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        // this requests the observable from the repo
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsCorrectData(){
        val projects = ProjectDataFactory.makeProjectList(2)
        // This creates an mock repo
        stubGetProjects(Observable.just(projects))
        // this requests the observable from the repo
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }
}
