package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsCache
import com.loc8r.data.models.ProjectEntity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import utils.DataFactory
import utils.ProjectFactory
import java.util.*

@RunWith(JUnit4::class)
class ProjectsCacheDateStoreTest {
    // A mocked cache
    private val cache = mock<ProjectsCache>()

    // the store being tested
    private val store = ProjectsCacheDataStore(cache)

    // Stubbing a response from the cache, returning the passed observable
    private fun stubProjectsCacheGetProjects(flowable: Flowable<List<ProjectEntity>>) {
        whenever(cache.getProjects())
                .thenReturn(flowable)
    }

    private fun stubSaveProjects(completable: Completable){
        whenever(cache.saveProjects(any()))
                .thenReturn(completable)
    }

    private fun stubCacheSetLastCacheTime(completable: Completable){
        whenever(cache.setLastCacheTime(any()))
                .thenReturn(completable)
    }

    private fun stubClearProjects(completable: Completable){
        whenever(cache.clearProjects())
                .thenReturn(completable)
    }

    private fun stubCacheGetBookmarkedProjects(flowable: Flowable<List<ProjectEntity>>){
        whenever(cache.getBookmarkedProjects())
                .thenReturn(flowable)
    }

    private fun stubCacheSetProjectAsBookmarked(completable: Completable){
        whenever(cache.setProjectAsBookmarked(any()))
                .thenReturn(completable)
    }

    private fun stubCacheSetProjectAsNotBookmarked(completable: Completable){
        whenever(cache.setProjectAsNotBookmarked(any()))
                .thenReturn(completable)
    }

    //testing the getProjects() method to see if it completes.
    @Test
    fun getProjectsCompletes(){
        stubProjectsCacheGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    //testing the getProjects() method to see returns the correct data.
    @Test
    fun getProjectsReturnsTheCorrectData(){
        val returnedData = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetProjects(Flowable.just(returnedData))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(returnedData)
    }

    @Test
    fun getProjectsServicedByCache(){
        val returnedData = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetProjects(Flowable.just(returnedData))
        val testObserver = store.getProjects().test()
        verify(cache).getProjects()
    }

    @Test
    fun saveProjectsCompletes(){
        stubSaveProjects(Completable.complete())
        stubCacheSetLastCacheTime(Completable.complete())
        val testObserver = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsServicedByCache(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubSaveProjects(Completable.complete())
        stubCacheSetLastCacheTime(Completable.complete())
        val testObserver = store.saveProjects(data).test()
        verify(cache).saveProjects(any())
    }

    @Test
    fun clearProjectsCompletes(){
        stubClearProjects(Completable.complete())
        val testObserver = store.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearProjectsServicedByCache(){
        stubClearProjects(Completable.complete())
        val testObserver = store.clearProjects().test()
        verify(cache).clearProjects()
    }

    @Test
    fun getBookmarkedProjectsCompletes(){
        stubCacheGetBookmarkedProjects(Flowable.just(listOf(
                ProjectFactory.makeProjectEntity())))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsServicedByCache(){
        stubCacheGetBookmarkedProjects(Flowable.just(listOf(
                ProjectFactory.makeProjectEntity())))
        val testObserver = store.getBookmarkedProjects().test()
        verify(cache).getBookmarkedProjects()
    }

    @Test
    fun getBookmarkedProjectsReturnsCorrectData(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubCacheGetBookmarkedProjects(Flowable.just(data))
        val testObserver = store.getBookmarkedProjects().test()
        testObserver.assertValue(data)
    }

    @Test
    fun setProjectAsBookmarkedCompletes(){
        stubCacheSetProjectAsBookmarked(Completable.complete())
        val testObserver = store.setProjectAsBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsBookmarkedServicedByCache(){
        val projectId = DataFactory.randomString()
        stubCacheSetProjectAsBookmarked(Completable.complete())
        val testObserver = store.setProjectAsBookmarked(projectId).test()
        verify(cache).setProjectAsBookmarked(projectId)
    }

    @Test
    fun setProjectAsNotBookmarkedCompletes(){
        stubCacheSetProjectAsNotBookmarked(Completable.complete())
        val testObserver = store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkedServicedByCache(){
        val projectId = DataFactory.randomString()
        stubCacheSetProjectAsNotBookmarked(Completable.complete())
        val testObserver = store.setProjectAsNotBookmarked(projectId).test()
        verify(cache).setProjectAsNotBookmarked(projectId)
    }

}