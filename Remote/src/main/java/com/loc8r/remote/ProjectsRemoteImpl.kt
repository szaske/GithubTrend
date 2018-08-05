/**
 * This class implements the ProjectsRemote interface from our Data layer.
 * The interface ProjectsRemote defines the functions required to request data
 * from a remote source.  The class will require 2 items in its constructor,
 * the service used to get the data, and the mapper used to transform the
 * data into something the Data layer can consume.
 *
 */
package com.loc8r.remote

import com.loc8r.data.interfaces.ProjectsRemote
import com.loc8r.data.models.ProjectEntity
import com.loc8r.remote.interfaces.GithubTrendService
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
        private val service: GithubTrendService,
        private val mapper: ProjectsResponseModelMapper)
    : ProjectsRemote {

    /**
     * This function first returns an Observable of a ProjectsResponseModel.
     * A responseModel is a list of ProjectModels, so I can I then map that
     * ResponseModel and get the it.items, which is each ProjectModel. For
     * each ProjectModel I can map each of them using the mapper.from function
     * to turn each ProjectModel into a ProjectEntity, which results in a
     * function that turns a Observable<ProjectsResponseModel> into a
     * Observable<List<ProjectsEntity>>
     */
    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc")
                .map {
                    it.items.map {
                        mapper.mapFromModel(it)
                    }
                }
    }
}