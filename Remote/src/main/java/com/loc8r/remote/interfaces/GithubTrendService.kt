/**
 * This is the interface used/required by Retrofit.  We need to define
 * the abstract function used to return a list of trending github repos
 *
 * This interface doesn't know anything about the base URL, but we need
 * to give it the sub-site we want to query which is 'search/repositories'
 *
 * Then we need to define any query parameters we might want to pass.
 * Finally we describe what sort of response we'll get back from the
 * github service, which is an Observable of ProjectResponse models
 * You can find more information here:
 * https://developer.github.com/v3/search/#search-repositories
 */
package com.loc8r.remote.interfaces

import com.loc8r.remote.models.ProjectsResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubTrendService {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String,
                           @Query("sort") sortBy:String,
                           @Query("order") order: String)
    : Observable<ProjectsResponseModel>
}