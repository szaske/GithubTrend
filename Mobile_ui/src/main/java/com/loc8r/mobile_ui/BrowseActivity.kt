package com.loc8r.mobile_ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.*
import com.loc8r.mobile_ui.adapters.BrowseAdapter
import com.loc8r.mobile_ui.di.ViewModelFactory
import com.loc8r.mobile_ui.interfaces.ProjectListener
import com.loc8r.presentation.BrowseProjectsViewModel
import javax.inject.Inject

class BrowseActivity: AppCompatActivity() {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var browseViewModel: BrowseProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        setupRecyclerView()
    }

    // A function to setup the Recycler View
    private fun setupRecyclerView(){
        // I'm able to skip findViewById() because I'm using the kotlin-android-extensions plugin
        // NICE
        recycler_projects.layoutManager  = LinearLayoutManager(this)
        browseAdapter.projectListener = projectListener
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