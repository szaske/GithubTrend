package com.loc8r.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.loc8r.cache.utils.ProjectDataFactory
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

// I don't believe closing the DB is needed since I'm using the inMemoryDB
//    @After
//    fun TestCleanUp(){
//        database.close()
//    }

    @Test
    fun getProjectsReturnsTheCorrectData(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(listOf(project))
    }

    @Test
    fun deleteProjectsClearsDB(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        val testObserverFull = database.cachedProjectsDao().getProjects().test()
        testObserverFull.assertValue(listOf(project))
        database.cachedProjectsDao().deleteProjects()
        val testObserverEmpty = database.cachedProjectsDao().getProjects().test()
        testObserverEmpty.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnsTheCorrectData(){
        val projectNotBM = ProjectDataFactory.makeNotBookmarkedCachedProject()
        val projectBM = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(projectNotBM, projectBM))
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(projectBM))
    }

    @Test
    fun setProjectAsBookmarkedSetsBookmarkCorrectly(){
        val projectNotBM = ProjectDataFactory.makeNotBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(projectNotBM))
        val testObserverNotBookmarked = database.cachedProjectsDao().getProjects().test()
        // Test to see if the project is in the data and returned
        testObserverNotBookmarked.assertValue(listOf(projectNotBM))

        database.cachedProjectsDao().setBookmarkStatus(true,projectNotBM.id)
        projectNotBM.isBookmarked = true // I set the project to bookmarked so the assert test passes
        val testObserverBookmarked = database.cachedProjectsDao().getProjects().test()
        testObserverBookmarked.assertValue(listOf(projectNotBM))
    }

    @Test
    fun setProjectAsNotBookmarkedSetsBookmarkCorrectly(){
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(bookmarkedProject))
        val testObserverBookmarked = database.cachedProjectsDao().getBookmarkedProjects().test()
        // Test to see if the project is in the data and returned
        testObserverBookmarked.assertValue(listOf(bookmarkedProject))

        database.cachedProjectsDao().setBookmarkStatus(false,bookmarkedProject.id)
        bookmarkedProject.isBookmarked = false // I set the project to not bookmarked so the assert test passes
        val testObserverNotBookmarked = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserverNotBookmarked.assertValue(emptyList())
    }
}