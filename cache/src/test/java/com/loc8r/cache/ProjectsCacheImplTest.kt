package com.loc8r.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.loc8r.cache.utils.ProjectDataFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.concurrent.TimeUnit

// I use the Robolectric Test runner because I'm going to be using parts of the Android framework
// And I need this accessible to me
@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

    // I add this rule from the Jetpack testing library because I'm using flowables in our tests,
    // this ensures that my tests are executed instantly, so I can retrieve the results and assert
    // the values that I get back from them.
    // see: https://developer.android.com/reference/android/arch/core/executor/testing/InstantTaskExecutorRule
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // I need a database and mapper to test the Projects Cache implementation, so I'll create them.
    // Here I use the Robolectric RuntimeEnvironment
    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val entityMapper = CachedProjectMapper()
    private val cache = ProjectsCacheImpl(database,entityMapper)

    @Test
    fun clearTablesCompletes(){
        val testObserver = cache.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsCompletes(){
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        val testObserver = cache.saveProjects(projects).test()
        testObserver.assertComplete()
    }

    // This test failed when ProjectsEntity was a class instead of a data class.
    @Test
    fun getProjectsReturnsTheCorrectData(){
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()
        val testObserver = cache.getProjects().test()
        testObserver.assertValue(projects)
    }

    // In this test we create 2 ProjectsEntities, one bookmarked and one not and then save them to
    // the database.  Then we request the bookmarked ones and see if that is returned
    @Test
    fun getBookmarkedProjectsReturnsTheCorrectData(){
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedProjectEntity()
        val projects = listOf(ProjectDataFactory.makeNonBookmarkedProjectEntity(), bookmarkedProject)
        cache.saveProjects(projects).test()
        val testObserver = cache.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun setProjectAsBookmarkedCompletes(){
        val nonBookmarkedProject = listOf(ProjectDataFactory.makeNonBookmarkedProjectEntity())
        cache.saveProjects(nonBookmarkedProject).test()
        val testObserver = cache.setProjectAsBookmarked(nonBookmarkedProject[0].id).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsNoTBookmarkedCompletes(){
        val bookmarkedProject = listOf(ProjectDataFactory.makeBookmarkedProjectEntity())
        cache.saveProjects(bookmarkedProject).test()
        val testObserver = cache.setProjectAsNotBookmarked(bookmarkedProject[0].id).test()
        testObserver.assertComplete()
    }

    @Test
    fun areProjectsCachedReturnsData(){
        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()
        val testObserver = cache.areProjectsCached().test()
        testObserver.assertValue(true)
    }

    @Test
    fun setLastCacheTimeCompletes(){
        val testObserver = cache.setLastCacheTime(1000L).test()
        testObserver.assertComplete()
    }

    // Since I'm not setting the cache, it should return Expired by default
    @Test
    fun isProjectsCacheExpiredReturnsExpiredWhenNull(){
        val testObserver = cache.isProjectsCacheExpired().test()
        testObserver.assertValue(true)
    }

    // Here I set the cache to 23 hours ago, so it should pass as false since the cache as
    // not yet expired
    @Test
    fun isProjectsCacheExpiredReturnsNotExpiredWhenWithin24Hours(){
        val cacheTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(23)
        cache.setLastCacheTime(cacheTime).test()
        val testObserver = cache.isProjectsCacheExpired().test()
        testObserver.assertValue(false)
    }

    // Here I set the cache to 25 hours ago, so it should pass as expired, since the expiration time
    // is set for 24 hours/ 1 day
    @Test
    fun isProjectsCacheExpiredReturnsExpiredWhenOldCacheTime(){
        val cacheTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(25)
        cache.setLastCacheTime(cacheTime).test()
        val testObserver = cache.isProjectsCacheExpired().test()
        testObserver.assertValue(true)
    }

}