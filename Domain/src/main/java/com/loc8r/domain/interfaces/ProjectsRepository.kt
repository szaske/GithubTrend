
/**
 *  Now that we have our Projects model, we need to create the interface for it.
 *  This interface will be implemented by an outside data layer, but here we want
 *  to explain what data we need for the domain module.  By implementing an interface
 *  we are insuring the separation of concerns
 *
 */

package com.loc8r.domain.interfaces

import com.loc8r.domain.models.Project
import io.reactivex.Completable
import io.reactivex.Observable

interface ProjectsRepository {

    // This will return an Observable list of projects
    fun getProjects(): Observable<List<Project>>

    // This method lets us bookmark a project, and returns a completeable, to let
    // us know that the task has completed
    fun bookmarkProject(projectId: String): Completable

    // This method unbookmarks a project
    fun unbookmarkProject(projectId: String): Completable

    // Method to return a list of bookmarkedProjects
    fun getBookmarkedProjects(): Observable<List<Project>>
}