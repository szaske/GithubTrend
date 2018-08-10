package com.loc8r.mobile_ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_browse.*

class BrowseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        setupRecyclerView()
    }

    // A function to setup the Recycler View
    private fun setupRecyclerView(){
        // I'm able to skip findViewById() because I'm using the kotlin-android-extensions plugin
        // NICE
        recycler_projects.layoutManager = LinearLayoutManager(this)
    }
}