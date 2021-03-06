/**
 * This interface will be used as a base interface for our data store classes.  I'm not exactly
 * sure why we created separate Cache and Remote interfaces if we have this base class, but
 * perhaps I figure it out as we go along.
 */

package com.loc8r.data.interfaces

import com.loc8r.data.models.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface ProjectsDataStore {

    fun getProjects(): Flowable<List<ProjectEntity>>

    fun saveProjects(projects: List<ProjectEntity>): Completable

    fun clearProjects(): Completable

    fun getBookmarkedProjects(): Flowable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable
}