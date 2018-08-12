package com.loc8r.mobile_ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_browse.*
import com.loc8r.mobile_ui.adapters.BrowseAdapter
import com.loc8r.mobile_ui.di.ViewModelFactory
import com.loc8r.mobile_ui.interfaces.ProjectListener
import com.loc8r.mobile_ui.models.Project
import com.loc8r.presentation.BrowseProjectsViewModel
import com.loc8r.presentation.models.ProjectView
import com.loc8r.presentation.utils.Resource
import com.loc8r.presentation.utils.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import javax.inject.Inject

class BrowseActivity: AppCompatActivity() {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var browseViewModel: BrowseProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)

        browseViewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(BrowseProjectsViewModel::class.java)

        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        // This Requests from the Presentation layer the liveData stream which is nothing
        browseViewModel.getProjects().observe(this,
                Observer<Resource<List<ProjectView>>> {

                    // When I get an observable pass IT to the handleData function
                    it?.let {
                        handleData(it)
                    }
                })

        // This tells the presentation layer to inject the bookmarked projects list into the LiveData
        // observable, so we can actually get project data
        browseViewModel.fetchProjects()
    }
    // A function to setup the Recycler View
    private fun setupRecyclerView(){
        // I'm able to skip findViewById() because I'm using the kotlin-android-extensions plugin
        // NICE
        recycler_projects.layoutManager  = LinearLayoutManager(this)
        browseAdapter.projectListener = projectListener
        recycler_projects.adapter = browseAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bookmark -> {
                startActivity(BookmarkedActivity.getStartIntent(this))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    // A function for showing or hiding the progress circle
    private fun handleData(resource: Resource<List<ProjectView>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data?.map {
                    mapper.mapToView(it)
                })
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?) {
        progress.visibility = View.GONE
        projects?.let {
            browseAdapter.projects = it
            browseAdapter.notifyDataSetChanged()
            recycler_projects.visibility = View.VISIBLE
        } ?: run {

        }
    }

    // An inner singleton class created to handle the OnClick events
    private val projectListener = object : ProjectListener {
        override fun onBookmarkedProjectClicked(projectId: String) {
            browseViewModel.unbookmarkProject(projectId)
        }

        override fun onProjectClicked(projectId: String) {
            browseViewModel.bookmarkProject(projectId)
        }

    }
}