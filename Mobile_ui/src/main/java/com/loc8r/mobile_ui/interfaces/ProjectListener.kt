/**
 * An Interface for click listening
 */
package com.loc8r.mobile_ui.interfaces

interface ProjectListener {
    fun onBookmarkedProjectClicked(projectId: String)

    fun onProjectClicked(projectId: String)
}