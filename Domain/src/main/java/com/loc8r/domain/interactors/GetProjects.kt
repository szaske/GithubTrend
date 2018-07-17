package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.models.Project
import com.loc8r.domain.interfaces.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 *  Constructor notes: The projectsRepository property is the
 *  abstracted access point to outside data layers.  It's an
 *  interface that's treated as a class, and a
 *  postExecutionThread, which is out name for a ReactiveX
 *  scheduler.  I assume we're changing the name to highlight that
 *  our Domain module should not know the implementation details
 *
 *  The semicolon in Kotlin class creation means that GetProjects
 *  inherits from the ObservableUseCaseBase base class.
 *
 *  We have no params, so we're using the Kotlin Nothing class.
 */
class GetProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCaseBase<List<Project>, Nothing>(postExecutionThread) {

    // here we override the parents method and replace it with a call to the
    // repository
    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
        return projectsRepository.getProjects()
    }

}