/**
 * If you look at the ProjectsRemote interface you'll see that it only implements
 * one method getProjects().  So for the other methods I'll just throw an exception
 * to show that something has been implemented incorrectly if someone attempt to
 * call these methods
 */

package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsDataStore
import com.loc8r.data.interfaces.ProjectsRemote
import com.loc8r.data.models.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class ProjectsRemoteDataStore @Inject constructor(
        private val projectsRemote: ProjectsRemote
): ProjectsDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("Saving projects is not supported by the remote data store")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clearing projects is not supported by the remote data store")
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        throw UnsupportedOperationException("Getting bookmarked projects is not supported by the remote data store")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Bookmarking a project is not supported by the remote data store")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("Unbookmarking a projects is not supported by the remote data store")
    }
}