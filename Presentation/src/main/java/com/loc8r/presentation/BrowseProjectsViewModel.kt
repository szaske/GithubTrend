package com.loc8r.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.loc8r.domain.interactors.BookmarkProject
import com.loc8r.domain.interactors.GetProjects
import com.loc8r.domain.interactors.UnbookmarkProject
import com.loc8r.domain.models.Project
import com.loc8r.presentation.models.ProjectView
import com.loc8r.presentation.utils.Resource
import com.loc8r.presentation.utils.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

// We inherit from the ViewModel class.  It has a function onCleared
// getProjects is the use case from the Domain module.  Each of these passed in dependencies is a
// domain Use case class, built on top of an Observable or Completable UseCaseBase class
open class BrowseProjectsViewModel @Inject constructor(
        private val getProjects: GetProjects?,
        private val bookmarkProject: BookmarkProject,
        private val unbookmarkProject: UnbookmarkProject,
        private val mapper: ProjectViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    init {
        fetchProjects()
    }

    // this disposes of any subscriptions as needed when the view is destroyed
    override fun onCleared() {
        getProjects?.dispose()
        super.onCleared()
    }

    // This retrieves my liveData
    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects(){
        // This simply sets the resource State to loading
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        return getProjects?.execute(ProjectSubscriber())!!
    }

    fun bookmarkProject(projectId: String){
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        return bookmarkProject.execute(BookmarkProjectsSubscriber(),
                BookmarkProject.Params.forProject(projectId))
    }

    fun unbookmarkProject(projectId: String){
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        return unbookmarkProject.execute(BookmarkProjectsSubscriber(),
                UnbookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectSubscriber: DisposableObserver<List<Project>>(){
        /**
         * Notifies the Observer that the [Observable] has finished sending push-based notifications.
         *
         *
         * The [Observable] will not call this method if it calls [.onError].
         */
        override fun onComplete() { }

        /**
         * Provides the Observer with a new item to observe.
         *
         *
         * The [Observable] may call this method 0 or more times.
         *
         *
         * The `Observable` will not call this method again after it calls either [.onComplete] or
         * [.onError].
         *
         * @param t
         * the item emitted by the Observable
         */
        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map {
                        mapper.mapToView(it)
                    },null))
        }

        /**
         * Notifies the Observer that the [Observable] has experienced an error condition.
         *
         *
         * If the [Observable] calls this method, it will not thereafter call [.onNext] or
         * [.onComplete].
         *
         * @param e
         * the exception encountered by the Observable
         */
        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null,e.localizedMessage))
        }

    }

    inner class BookmarkProjectsSubscriber: DisposableCompletableObserver() {
        /**
         * Called once the deferred computation completes normally.
         */
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    liveData.value?.data,null))
        }

        /**
         * Called once if the deferred computation 'throws' an exception.
         * @param e the exception, not null.
         */
        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, liveData.value?.data,
                    e.localizedMessage))
        }

    }
}