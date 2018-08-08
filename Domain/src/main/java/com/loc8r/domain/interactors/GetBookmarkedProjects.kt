/**
 * In Clean Architecture, the Domain layer defines Use Cases
 * which define operations which can be performed in our
 * application. In this lesson, we’ll be implementing the Get
 * Bookmarked Projects Use Cases class for our application so
 * that we can retrieve a list of bookmarked projects within
 * our external data source through our domain layer.
 */

package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.interactors.ObservableUseCaseBase
import com.loc8r.domain.models.Project
import com.loc8r.domain.interfaces.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetBookmarkedProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCaseBase<List<Project>, Nothing>(postExecutionThread){

    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getBookmarkedProjects()
    }

}