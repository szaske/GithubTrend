/**
 * This class helps us choose between 2 passed data stores
 *
 * Unit tests can be found here:
 * @see com.loc8r.data.ProjectsDateStoreFactoryTest
 *
 */
package com.loc8r.data

import com.loc8r.data.interfaces.ProjectsDataStore
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
        private val projectsCacheDataStore: ProjectsCacheDataStore,
        private val projectsRemoteDataStore: ProjectsRemoteDataStore) {

    /**
     * Returns the proper data store depending on the status of the cache
     */
    open fun getDataStore(projectsCached: Boolean,
                          cacheExpired: Boolean): ProjectsDataStore {
        return if (projectsCached && !cacheExpired) {
            projectsCacheDataStore
        } else {
            projectsRemoteDataStore
        }
    }

    open fun getCachedDataStore(): ProjectsDataStore {
        return projectsCacheDataStore
    }
}