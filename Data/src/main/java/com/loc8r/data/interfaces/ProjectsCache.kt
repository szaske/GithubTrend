/**
 * Within Clean Architecture, the Data layer allows us to abstract the sources
 * of the data that our application uses. We’re creating this interface that
 * enforce the operations to be implemented by external cache data source.
 */

package com.loc8r.data.interfaces

import com.loc8r.data.models.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ProjectsCache {

    // This clears the Projects cache
    fun clearProjects(): Completable

    // This saves Projects to the cache from the remote database
    fun saveProjects(projects: List<ProjectEntity>): Completable

    // This gets us the Observable list of Projects
    fun getProjects(): Observable<List<ProjectEntity>>

    // This gets us the Observable list of Bookmarked Projects
    fun getBookmarkedProjects(): Observable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable

    fun areProjectsCached(): Single<Boolean>

    fun setLastCacheTime(lastCache: Long): Completable

    fun isProjectsCacheExpired(): Single<Boolean>

}