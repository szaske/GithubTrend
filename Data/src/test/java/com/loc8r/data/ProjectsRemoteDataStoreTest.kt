package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsRemote
import com.loc8r.data.models.ProjectEntity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Test
import utils.DataFactory
import utils.ProjectFactory

class ProjectsRemoteDataStoreTest {
    private val remote = mock<ProjectsRemote>()
    private val store = ProjectsRemoteDataStore(remote)

    // Stub Methods
    //

    private fun stubRemoteGetProjects(flowable: Flowable<List<ProjectEntity>>){
        whenever(remote.getProjects())
                .thenReturn(flowable)
    }

    @Test
    fun GetProjectsCompletes(){
        stubRemoteGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun GetProjectsReturnsCorrectData(){
        val response = listOf(ProjectFactory.makeProjectEntity())
        stubRemoteGetProjects(Flowable.just(response))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(response)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjectsThrowsException(){
        store.saveProjects(listOf()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjectsThrowsException(){
        store.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getBookmarkedProjectsThrowsException(){
        store.getBookmarkedProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsBookmarkedThrowsException(){
        store.setProjectAsBookmarked(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectAsNotBookmarkedThrowsException(){
        store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
    }


}