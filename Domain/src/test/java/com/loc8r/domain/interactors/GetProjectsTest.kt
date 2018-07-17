/**
 *  Unit tests for the Domain module interactor's
 *  For some reason you need to run:
 *  gradlew :domain:test
 *  the first time to get this to run correctly.  Without it you'll
 *  get a:
 *
 *  Class not found: "com.xxx.xxx" Empty test suite. error.
 */

package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.interactors.GetProjects
import com.loc8r.domain.models.Project
import com.loc8r.domain.interfaces.ProjectsRepository
import com.loc8r.domain.interactors.factory.ProjectDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class GetProjectsTest {
    // The Class itself, lateinit'ed because it will be initialized
    // after our mocks
    private lateinit var  getProjects: GetProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    // Because our tests will be interacting with the projectsRepository
    // we'll need to create a mock observable.  This method creates
    // a mock Repository that returns the given observable
    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getProjects())
                .thenReturn(observable)
    }

    @Before
    fun setup() {
        // Initializing our mocks
        MockitoAnnotations.initMocks(this)

        // Instantiating our test class with the mocks
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    // This tests to see that the getProjects call to the
    // The just() method emits its parameter(s) as OnNext notifications,
    // and after that, it emits an OnCompleted notification.
    // see: https://medium.com/@vanniktech/testing-rxjava-code-made-easy-4cc32450fc9a
    @Test
    fun getProjectsCompletes(){
        // This creates a event listener for getProjects that mocks an Observable response.
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))

        //then we call the RxJava test method which helps us test observables
        val testObserver = getProjects.buildUseCaseObservable().test()

        // This tests to see if the Observer completed
        testObserver.assertComplete()
    }

    // This test checks to see if the correct data is returned from the observable
    @Test
    fun getProjectsReturnsCorrectData(){
        // First we create a variable that represents the correct data
        val projects = ProjectDataFactory.makeProjectList(2)
        // This creates a event listener for getProjects that mocks an Observable response.
        stubGetProjects(Observable.just(projects))

        //then we call the RxJava test method which helps us test observables
        val testObserver = getProjects.buildUseCaseObservable().test()

        // This tests to see if the Observer got the data we sent it.
        testObserver.assertValue(projects)
    }
}