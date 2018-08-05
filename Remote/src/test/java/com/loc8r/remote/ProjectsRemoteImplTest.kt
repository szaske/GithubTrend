/**
 * Unit test for the Remote ProjectsRemoteImpl...the part of the Remote module that implements
 * the Github API and returns the Project data from Github
 */

package com.loc8r.remote

import com.loc8r.data.models.ProjectEntity
import com.loc8r.remote.interfaces.GithubTrendService
import com.loc8r.remote.models.ProjectModel
import com.loc8r.remote.models.ProjectsResponseModel
import com.loc8r.remote.utils.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Flowable.just
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteImplTest {

    // I create mocks of the mapper and service as they are out of scope for these unit tests
    private val mapper = mock<ProjectsResponseModelMapper>()
    private val service = mock<GithubTrendService>()
    private val remote = ProjectsRemoteImpl(service,mapper)

    // A fake returned observable from the service when searchRepo is requested
//    private fun stubServiceSearchRepositories(observable: Observable<ProjectsResponseModel>){
//        whenever(service.searchRepositories(any(), any(), any()))
//                .thenReturn(observable)
//    }
    private fun stubServiceSearchRepositories(flowable: Flowable<ProjectsResponseModel>){
        whenever(service.searchRepositories(any(), any(), any()))
                .thenReturn(flowable)
    }

    private fun stubMapperMapFromModel(model: ProjectModel, entity: ProjectEntity){
        whenever(mapper.mapFromModel(model))
                .thenReturn(entity)
    }

    @Before
    fun setUp() {
    }

    @Test
    fun getProjectsCompletes() {
        stubServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectsResponse()))
        //Does not seem to be needed as the function call completes without it, but added anyway
        stubMapperMapFromModel(any(),ProjectDataFactory.makeProjectEntity())
        val testObserver = remote.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsCallsServer(){
        stubServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectsResponse()))
        //Does not seem to be needed as the function call completes without it, but added anyway
        stubMapperMapFromModel(any(),ProjectDataFactory.makeProjectEntity())
        val testObserver = remote.getProjects().test()
        verify(service).searchRepositories(any(), any(), any())
    }

    @Test
    fun getProjectsReturnsCorrectData(){
        val response = ProjectDataFactory.makeProjectsResponse()
        stubServiceSearchRepositories(Flowable.just(response))

        /**
         * At this point response is an Observable of ProjectsResponseModel
         * but we need to convert it to a list of ProjectEntities, so we have to
         * do that conversion in the unit test below
         */
        val entities = mutableListOf<ProjectEntity>()
        response.items.forEach {
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)
            stubMapperMapFromModel(it,entity)
        }
        val testObserver = remote.getProjects().test()
        testObserver.assertValue(entities)
    }

    @Test
    fun getProjectsCallsServerWithCorrectParameters(){
        stubServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectsResponse()))
        //Does not seem to be needed as the function call completes without it, but added anyway
        stubMapperMapFromModel(any(),ProjectDataFactory.makeProjectEntity())
        val testObserver = remote.getProjects().test()
        verify(service).searchRepositories("language:kotlin", "stars", "desc")
    }
}