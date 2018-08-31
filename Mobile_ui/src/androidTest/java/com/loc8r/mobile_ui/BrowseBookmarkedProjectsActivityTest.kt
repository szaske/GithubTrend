package com.loc8r.mobile_ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.loc8r.domain.models.Project
import com.loc8r.mobile_ui.adapters.BookmarkedAdapter
import com.loc8r.mobile_ui.di.TestApplication
import com.loc8r.mobile_ui.utils.TestProjectFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// Apparently using the AndroidJUnit4 annotation tells Android Studio to use the default test
// runner, or whatever is named under testInstrumentationRunner in the build.gradle file.
@RunWith(AndroidJUnit4::class)
class BrowseBookmarkedProjectsActivityTest {

    // This determines what activity will be used in the test.
    // Instructs the Kotlin compiler not to generate getters/setters for this
    // property and expose it as a field.
    @get:Rule @JvmField
    val activity = ActivityTestRule<BookmarkedActivity>(BookmarkedActivity::class.java,false,false)

    // Here I'm going to use the TestApplication to retrieve the
    private fun stubProjectsRepositoryGetBookmarkedProjects(observable: Observable<List<Project>>) {
        whenever(TestApplication.appComponent().projectsRepository().getBookmarkedProjects())
                .thenReturn(observable)
    }

    @Test
    fun activityLaunches(){
        // This fakes our getProjects call and delivers a list of one project
        stubProjectsRepositoryGetBookmarkedProjects(Observable.just(listOf(
                TestProjectFactory.makeProject())))
        // This launches the activity
        activity.launchActivity(null)
    }

    @Test
    fun projectsDisplayCorrectly() {
        val projects = listOf(TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject())
        stubProjectsRepositoryGetBookmarkedProjects(Observable.just(projects))
        activity.launchActivity(null)

        projects.forEachIndexed { index, project ->
            // First I'll scroll to the specific item in the list, incase this screen does
            // not show this project on the screen
            onView(withId(R.id.recycler_projects_bookmarked))
                    .perform(RecyclerViewActions.scrollToPosition<BookmarkedAdapter.ViewHolder>(index))

            // then Assert if the full name of the project is shown
            onView(withId(R.id.recycler_projects_bookmarked))
                    .check(matches(hasDescendant(withText(project.fullName))))
                    .check(matches(hasDescendant(withText(project.ownerName))))
        }
    }
}