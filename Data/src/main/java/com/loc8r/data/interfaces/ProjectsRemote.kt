/**
 *  An interface for the remote database
 */

package com.loc8r.data.interfaces

import com.loc8r.data.models.ProjectEntity
import io.reactivex.Observable

interface ProjectsRemote {

    fun getProjects(): Observable<List<ProjectEntity>>
}