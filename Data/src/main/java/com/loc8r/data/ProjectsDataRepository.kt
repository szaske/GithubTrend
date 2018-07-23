/**
 * This class is the data layers main connection point with the domain layer.
 * You can see this because it derives from the ProjectsRepository interface
 * and uses the mapper interface.  The mapper interface ProjectMapper helps
 * me translate the 2 different Projects models.  The Domain model is called
 * Project and the Data model is called ProjectEntity.
 *
 */
package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsCache
import com.loc8r.domain.interfaces.ProjectsRepository
import com.loc8r.domain.models.Project
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * We're passing three items in the constuctor;
 * 1. mapper is the class that helps us convert from
 * and to the Data ProjectEntity and the Domain Project class types.
 * 2. ProjectsCache is the item that can give us information about the cache
 * 3. The factory, that determines our data store
 */
class ProjectsDataRepository @Inject constructor(
        private val mapper: ProjectMapper,
        private val cache: ProjectsCache,
        private val factory: ProjectsDataStoreFactory)
    : ProjectsRepository {

    /**
     * There is a lot happening here so it's the long description.
     *
     * Because we're going to be getting projects data from our data store,
     * we first need to get some information about the cache.  The ZIP method
     * takes two observables, casts a function on them and returns a new
     * observable.  In this case the BiFunction is taking our two
     * boolean observables and turning them into a Pair.
     *
     * it then passes this pair to the factory to determine where we should
     * get the data.  The Factory determines which store to use and returns it
     * and then we cast the getProjects method from the store...which returns
     * an Observable list of projects.  It's important to note that these projects
     * are in the ProjectEntity model
     *
     * The second flatmap takes this list, and saves them to the cache.
     * Since this method returns a completable and not an observable, I added
     * the andthen method to return an observable instead...so we can continue
     * the chain.
     *
     * Finally we need to use the map method to convert each list, and even a
     * map within the map to convert each ProjectEntity into a Project for
     * use within the Domain layer.
     *
     * The results is a Observable of Projects
     */
    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCached().toObservable(),
                cache.isProjectsCacheExpired().toObservable(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> {
                    areCached, isExpired -> Pair(areCached,isExpired)
                })
                .flatMap {
                    factory.getDataStore(it.first, it.second)
                            .getProjects()
                }
                .flatMap { projects ->
                    factory.getCachedDataStore()
                            .saveProjects(projects)
                            .andThen(Observable.just(projects))
                }
                .map { eachList ->
                    eachList.map { eachProjectEnt ->
                        mapper.mapFromEntity(eachProjectEnt)
                    }
                }
    }

    /**
     * this method is simpler since it just returns a completable
     */
    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCachedDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unbookmarkProject(projectId: String): Completable {
        return factory.getCachedDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCachedDataStore().getBookmarkedProjects()
                .map { eachList ->
                    eachList.map {
                        mapper.mapFromEntity(it)
                    }
                }
    }
}