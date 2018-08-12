package com.loc8r.mobile_ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.loc8r.mobile_ui.adapters.BookmarkedAdapter
import com.loc8r.mobile_ui.di.ViewModelFactory
import com.loc8r.presentation.BrowseBookmarkedProjectsViewModel
import com.loc8r.presentation.models.ProjectView
import com.loc8r.presentation.utils.Resource
import com.loc8r.presentation.utils.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_bookmarked.*
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BookmarkedActivity: AppCompatActivity() {

    @Inject lateinit var adapter: BookmarkedAdapter
    @Inject lateinit var mapper: ProjectViewMapper // A mapper to convert projects from the presentation layer
    @Inject lateinit var viewModelFactory: ViewModelFactory // to be able to create the viewModel I need
    lateinit var browseViewModel: BrowseBookmarkedProjectsViewModel // The factory will instantiate this when needed

    // This is a convenience method that helps outside classes launch this activity
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context,BookmarkedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * This is our Dagger Inject method. Inversion of Control: The client delegates the
         * responsibility of providing its dependencies to external code (the injector).
         * The client code does not need to know about the injecting code.
         */
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)

        /**
         * We now have a ViewModel can can get the Bookmarked projects in onStart
         */
        browseViewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(BrowseBookmarkedProjectsViewModel::class.java)

        // initialize the RecyclerView (in it's own separate function
        setupBrowseRecycler()

        //Add an up icon to return to Browsing all Projects
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupBrowseRecycler(){
        recycler_projects_bookmarked.layoutManager = LinearLayoutManager(this)
    }

    // A function for showing or hiding the progress circle
    private fun handleData(resource: Resource<List<ProjectView>>){
        when(resource.status){
            ResourceState.SUCCESS -> {
                progress_bookmarked.visibility = View.GONE
                recycler_projects_bookmarked.visibility = View.VISIBLE

                // now take the data from the resource, convert with the mapper and
                // apply it to the adapter
                resource.data?.let {
                    adapter.projects = it.map {
                        mapper.mapToView(it)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
            ResourceState.LOADING -> {
                progress_bookmarked.visibility = View.VISIBLE
                recycler_projects_bookmarked.visibility = View.GONE
            }
            ResourceState.ERROR -> {

            }
        }
    }
}