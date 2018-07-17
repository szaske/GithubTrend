package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.interfaces.ProjectsRepository
import com.loc8r.domain.interactors.factory.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BookmarkProjectTest {
    // This is the domain class that allows us to bookmark a project
    private lateinit var bookmarkProject: BookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var  postExecutionThread: PostExecutionThread

    // This stub function returns the passed completable whenever the
    // bookmarkProject method is invoked.  By using this stub we are
    // removing the repo out of the test...so it is not being tested
    private fun stubBookmarkProject(completable: Completable) {
        whenever(projectsRepository.bookmarkProject(any()))
                .thenReturn(completable)
    }

    @Before fun setup(){
        // This instantiates our mocks
        MockitoAnnotations.initMocks(this)
        bookmarkProject = BookmarkProject(projectsRepository,postExecutionThread)

    }

    @Test fun bookmarkProjectCompletes(){
        // this replaces the repo in our test and returns a Completable
        // instance that completes immediately when subscribed to.
        stubBookmarkProject(Completable.complete())

        // Here we create an instance of the bookmarkProject class and call the method
        // using a random UUid from our Data factory
        val testObserver = bookmarkProject.buildUseCaseCompletable(
                BookmarkProject.Params.forProject(ProjectDataFactory.randomUUid())
        ).test()
        // this tests to see that the observable completed
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectThrowsExceptionWhenNoParamsPassed(){
        // This makes the class and attempts to bookmark a project.  The Test method then creates
        // a observer and subscribes to the observable.  Since we are not passing any params
        // we should get an exception when this test runs
        bookmarkProject.buildUseCaseCompletable().test()
    }
}