/**
 * Unit tests for the ProjectsDataRepository.
 * The PDR requires a mapper, cache and factory to be instantiated
 * so I'll need to mock them to test the PDR.  The factory just
 * returns a DataStore so I'll also need to mock that well.
 * so tests will use this process.
 * 1. Mock all 4
 * 2. Stub the mock factory to return the mock store
 */
package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsCache
import com.loc8r.data.interfaces.ProjectsDataStore
import com.loc8r.data.models.ProjectEntity
import com.loc8r.domain.models.Project
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import utils.DataFactory
import utils.ProjectFactory

@RunWith(JUnit4::class)
class ProjectsDataRepositoryTest {
    // to convert project classes
    private val mapper = mock<ProjectMapper>()
    // factory helps us pick the correct store
    private val factory = mock<ProjectsDataStoreFactory>()
    // a mock store to be returned by the factory
    private val store = mock<ProjectsDataStore>()
    // the cache version of the store
    private val cache = mock<ProjectsCache>()
    // the repository under test.  Since we're using dependency injection
    // we pass the 3 items needed to create the repo
    private val repository = ProjectsDataRepository(mapper,cache,factory)

    /**
     * Test Stub functions
     *
     **/
    // Because we're mocking the factory we need to stub the response
    private fun stubFactoryGetDataStore() {
        whenever(factory.getDataStore(any(), any()))
                .thenReturn(store)
    }

    // Stub to support the getCachedDataStore method. It still returns
    // our mock store.
    private fun stubFactoryGetCacheDataStore() {
        whenever(factory.getCachedDataStore())
                .thenReturn(store)
    }

    // because we're mocking the mapper we need to stub the response.
    private fun stubMapper(model: Project, entity: ProjectEntity){
        whenever(mapper.mapFromEntity(entity))
                .thenReturn(model)
    }

    // because we're mocking the store we need to stub the response,
    // which will be the observable we pass into the stub
    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>){
        whenever(store.getProjects())
                .thenReturn(observable)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>){
        whenever(store.getBookmarkedProjects())
                .thenReturn(observable)
    }

    private fun stubSaveProjects(completable: Completable) {
        whenever(store.saveProjects(any()))
                .thenReturn(completable)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>){
        whenever(cache.isProjectsCacheExpired())
                .thenReturn(single)
    }

    private fun stubAreProjectsCached(single: Single<Boolean>){
        whenever(cache.areProjectsCached())
                .thenReturn(single)
    }

    private fun stubBookmarkProject(completable: Completable){
        whenever(store.setProjectAsBookmarked(any()))
                .thenReturn(completable)
    }

    private fun stubUnbookmarkProject(completable: Completable){
        whenever(store.setProjectAsNotBookmarked(any()))
                .thenReturn(completable)
    }

    @Before
    fun setup(){
        // before each test we'll create a factory, which returns the store
        stubFactoryGetDataStore()
        // And I'll prep the request for the cacheDataStore
        stubFactoryGetCacheDataStore()
        //preps mock cache to say Projects are not cached
        stubAreProjectsCached(Single.just(false))
        // preps mock cache to say cache is not expired
        stubIsCacheExpired(Single.just(false))
        // And stub a response to save projects, which just returns a completed completable
        stubSaveProjects(Completable.complete())
    }

    /**
     * This is a complex test and requires a bunch of mocks.  Here are
     * the steps required to test the PDR getProjects method
     * 1. The PDR needs a mock mapper, cache and factory
     * 2. Since the factory returns a store I'll mock that too
     * 3. We need the factory to return the store, so I stub that in
     * the @before
     *
     * Here are the calls that happen on getProjects.  I'll need to stub
     * the responses to each one to unit test the method
     * 1. cache.areProjectsCached()
     * 2. cache.isProjectsCacheExpired()
     * 3. factory.getDataStore()
     * 4. store.getProjects()
     * 5. factory.getCachedDataStore()
     * 6. store.saveProjects(projects)
     * 7. mapper.mapFromEntity()
     *
     * I'll need stub to support each of these calls
     */
    @Test
    fun getProjectsCompletes() {
        // Setup function takes care of #1, #2, #3, #5

        // this preps #4 above
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))

        // Stub our mapper to return a project #7
        stubMapper(ProjectFactory.makeProject(), any())

        // Create a repo and call the method under test (getProjects)
        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsTheCorrectData(){
        // make a project and projectEntity
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()

        // preps the store to return an observable of one list of one entity
        stubGetProjects(Observable.just(listOf(projectEntity)))

        // preps the mapper to return a project when asked to convert an entity
        stubMapper(project,projectEntity)

        val testObserver = repository.getProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        // Setup function takes care of #1, #2, #3, #5

        // this preps #4 above
        stubGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))

        // Stub our mapper to return a project #7
        stubMapper(ProjectFactory.makeProject(), any())

        // Create a repo and call the method under test (getProjects)
        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsTheCorrectData(){
        // make a project and projectEntity
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()

        // preps the store to return an observable of one list of one entity
        stubGetBookmarkedProjects(Observable.just(listOf(projectEntity)))

        // preps the mapper to return a project when asked to convert an entity
        stubMapper(project,projectEntity)

        val testObserver = repository.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))
    }

    // Stubs needed include:
    // factory.getCachedDataStore() which is handled in the setup function
    @Test
    fun bookmarkProjectCompletes(){
        stubBookmarkProject(Completable.complete())

        val testObserver = repository.bookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun unbookmarkProjectCompletes(){
        stubUnbookmarkProject(Completable.complete())

        val testObserver = repository.unbookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

}