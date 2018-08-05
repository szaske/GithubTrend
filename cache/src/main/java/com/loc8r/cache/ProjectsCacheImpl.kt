/**
 * This is that class that will implement the ProjectsCache interface for the Data layer.  This
 * class creates a way for the Cache layer to speak to the Data layer/module.  The constructor requires
 * a Database and mapper, so we pass them in so that we are injection dependencies (DI)
 */

package com.loc8r.cache

import com.loc8r.cache.model.Config
import com.loc8r.data.interfaces.ProjectsCache
import com.loc8r.data.models.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
        private val projectsDatabase: ProjectsDatabase,
        private val mapper: CachedProjectMapper)
    : ProjectsCache {

    // This asks the cache to clear the projects and then completes.  defer just waits until an
    // observer subscribes to the completable before beginning
    override fun clearProjects(): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer{
                projectsDatabase.cachedProjectsDao().insertProjects(
                        projects.map{
                            mapper.mapToCache(it) })
                 Completable.complete()
        }
    }

    // This function gets the list of Projects and then maps them to ProjectEntities
    // for the data layer
    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getProjects()
                .map{
                    it.map {
                        mapper.mapFromCache(it)
                    }
                }

    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
                .map {
                    it.map {
                        mapper.mapFromCache(it)
                    }
                }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkStatus(true,projectId)
            Completable.complete()
        }
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkStatus(false,projectId)
            Completable.complete()
        }
    }

    // map !it just reverses the answer, since our question is asking if projects exist and
    // isEmpty is the opposite question.
    override fun areProjectsCached(): Single<Boolean> {
        return projectsDatabase.cachedProjectsDao().getProjects().isEmpty
                .map { !it }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer(){
            projectsDatabase.configDao().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()

        // Joe's version of this line,  I don't believe this is correct
        // val expirationTime = (60 * 10 * 1000).toLong()

        // my version
        val expirationTime = TimeUnit.DAYS.toMillis(1)

        return projectsDatabase.configDao().getConfig()
                .onErrorReturn { Config(lastCacheTime = 0) }
                .map {
                    currentTime - it.lastCacheTime > expirationTime
                }
    }
}