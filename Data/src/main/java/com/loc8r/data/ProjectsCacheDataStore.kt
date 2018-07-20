/**
 * You'll see that I pass the projectsCache interface in the constructor
 * so what gets passed becomes the mechanism by which the cache is implemented
 */
package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsCache
import com.loc8r.data.interfaces.ProjectsDataStore
import com.loc8r.data.models.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class ProjectsCacheDataStore @Inject  constructor(
        private val projectsCache: ProjectsCache
): ProjectsDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsCache.getProjects()
    }

    /**
     * This method both saves the project and then sets the last cache time
     */
    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return projectsCache.saveProjects(projects)
                .andThen(projectsCache.setLastCacheTime(System.currentTimeMillis()))
    }

    override fun clearProjects(): Completable {
        return projectsCache.clearProjects()
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
     return projectsCache.getBookmarkedProjects()
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsBookmarked(projectId)
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsNotBookmarked(projectId)
    }
}