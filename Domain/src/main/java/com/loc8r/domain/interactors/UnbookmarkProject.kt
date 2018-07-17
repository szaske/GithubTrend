/**
 *  Since this class does not retrieve any data I'll inherit from the Completable
 *  base class.
 */

package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import com.loc8r.domain.interactors.CompletableUseCaseBase
import com.loc8r.domain.interfaces.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

class UnbookmarkProject @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread)
    : CompletableUseCaseBase<UnbookmarkProject.Params>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: Params?): Completable {

        // We need to check if params are null, because bookmarking a
        // project requires a project ID
        if(params == null) throw IllegalArgumentException("Params cannot be null")
        return projectsRepository.unbookmarkProject(params.projectId)
    }

    // An Inner class
    data class Params constructor(val projectId: String){
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }
}