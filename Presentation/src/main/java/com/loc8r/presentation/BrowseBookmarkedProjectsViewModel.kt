package com.loc8r.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.loc8r.domain.interactors.GetBookmarkedProjects
import com.loc8r.domain.models.Project
import com.loc8r.presentation.models.ProjectView
import com.loc8r.presentation.utils.Resource
import com.loc8r.presentation.utils.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
        private val getBookmarkedProjects: GetBookmarkedProjects,
        private val mapper: ProjectViewMapper): ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    // This retrieves my liveData
    fun getProjects(): LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects(){
        // This sets the resource State to loading
        liveData.postValue(Resource(ResourceState.LOADING,null,null))

        // and then asks the Domain layer to return the bookmarked Projects into the LiveData stream
        // which it does through the ProjectsSubscriber inner class
        return getBookmarkedProjects.execute(ProjectsSubscriber())
    }

    // An inner class so we can create a disposable observer class to retrieve the results of our
    // execution
    inner class ProjectsSubscriber: DisposableObserver<List<Project>>(){
        /**
         * Notifies the Observer that the [Observable] has finished sending push-based notifications.
         *
         *
         * The [Observable] will not call this method if it calls [.onError].
         */
        override fun onComplete() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

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
            liveData.postValue(Resource(ResourceState.ERROR,null,e.localizedMessage))
        }

    }
}