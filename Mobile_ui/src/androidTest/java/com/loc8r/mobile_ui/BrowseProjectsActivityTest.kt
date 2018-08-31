package com.loc8r.mobile_ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.loc8r.domain.models.Project
import com.loc8r.mobile_ui.adapters.BrowseAdapter
import com.loc8r.mobile_ui.di.TestApplication
import com.loc8r.mobile_ui.utils.TestProjectFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BrowseProjectsActivityTest {

    // This determines what activity will be used in the test
    @get:Rule
    val activity = ActivityTestRule<BrowseActivity>(BrowseActivity::class.java,false,false)

    // Here I'm going to use the TestApplication to retrieve the
    private fun stubProjectsRepositoryGetProjects(observable: Observable<List<Project>>) {
        whenever(TestApplication.appComponent().projectsRepository().getProjects())
                .thenReturn(observable)
    }

    @Test
    fun activityLaunches(){
        // This fakes our getProjects call and delivers a list of one project
        stubProjectsRepositoryGetProjects(Observable.just(listOf(TestProjectFactory.makeProject())))
        // This launches the activity
        activity.launchActivity(null)
    }

    @Test
    fun projectsDisplayCorrectly() {
        val projects = listOf(TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject(),
                TestProjectFactory.makeProject(),TestProjectFactory.makeProject())
        stubProjectsRepositoryGetProjects(Observable.just(projects))
        activity.launchActivity(null)
        
        projects.forEachIndexed { index, project ->
            // First I'll scroll to the specific item in the list, incase this screen does
            // not show this project on the screen
            onView(withId(R.id.recycler_projects))
                    .perform(RecyclerViewActions.scrollToPosition<BrowseAdapter.ViewHolder>(index))

            // then Assert if the full name of the project is shown
            onView(withId(R.id.recycler_projects))
                    .check(matches(hasDescendant(withText(project.fullName))))
                    .check(matches(hasDescendant(withText(project.ownerName))))

        }
    }
}