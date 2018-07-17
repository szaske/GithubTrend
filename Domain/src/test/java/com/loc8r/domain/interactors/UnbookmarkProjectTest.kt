package com.loc8r.domain.interactors

import com.loc8r.domain.interactors.factory.ProjectDataFactory
import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.interfaces.ProjectsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnbookmarkProjectTest {
    private  lateinit var unbookmarkProject: UnbookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    private fun stubUnbookmarkProject(completable: Completable) {
        whenever(projectsRepository.unbookmarkProject(any()))
                .thenReturn(completable)
    }
    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        // This creates our class that is being tested, along with the injected
        // requirements for that class
        unbookmarkProject = UnbookmarkProject(projectsRepository, postExecutionThread)
    }

    @Test
    fun unbookmarkProjectCompletes(){
        // This calls our stub method and sends a generic completable
        stubUnbookmarkProject(Completable.complete())
        val testObserver = unbookmarkProject.buildUseCaseCompletable(
                UnbookmarkProject.Params.forProject(ProjectDataFactory.randomUUid())
        ).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun unbookmarkProjectThrowsExceptionWhenNoParamsPassed(){
        // no stub is needed since we just want to test the params
        // check in the method
        unbookmarkProject.buildUseCaseCompletable().test()
    }
}