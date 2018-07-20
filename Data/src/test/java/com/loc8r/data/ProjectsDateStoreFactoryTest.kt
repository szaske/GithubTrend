/**
 * Unit tests to check the logic represented in the ProjectsDataStoreFactory.
 * This class is meant to choose the correct store depending on the status
 * of the cache, so these tests will test that logic
 */

package com.loc8r.data

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import kotlin.test.assertEquals

class ProjectsDateStoreFactoryTest {
    private val cacheStore = mock<ProjectsCacheDataStore>()
    private val remoteStore = mock<ProjectsRemoteDataStore>()
    private val factory = ProjectsDataStoreFactory(cacheStore,remoteStore)

    @Test
    fun getDataStoreReturnsRemoteStoreWhenCacheHasExpired(){
        assertEquals(remoteStore,factory.getDataStore(true,true))
    }

    @Test
    fun getDataStoreReturnsRemoteStoreWhenProjectsNotCached(){
        assertEquals(remoteStore,factory.getDataStore(false,false))
    }

    @Test
    fun getDataStoreReturnsCacheStoreWhenProjectsCachedAndNotExpired(){
        assertEquals(cacheStore,factory.getDataStore(true,false))
    }

    @Test
    fun getCacheDataStoreReturnsCacheStore(){
        assertEquals(cacheStore,factory.getCachedDataStore())
    }

}