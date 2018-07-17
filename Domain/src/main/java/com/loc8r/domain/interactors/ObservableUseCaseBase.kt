
/**
 *  Within Clean Architecture, the Domain layer defines Use Cases
 *  which define operations which can be performed in our Android
 *  project. In this lesson, we’ll be creating some BASE classes
 *  which these Use Cases will inherit from to save us writing
 *  boilerplate code repeatedly in our Use Cases implementations.
 */

package com.loc8r.domain.interactors

import com.loc8r.domain.interfaces.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

// defined with T generics, so the class works with various Observable types
abstract class ObservableUseCaseBase<T, in Params> constructor(
        // an abstraction for the schedule thread for each observable
        // required in the constructor
        private val postExecutionThread: PostExecutionThread) {

    // A reference to the disposable observable
    private val disposables = CompositeDisposable()

    // A method for creating a observable with parameters
    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    // A method for executing the observable.  This does a bunch of stuff
    // 1. Creates the observable
    // 2. selects the scheduler (see http://reactivex.io/documentation/operators/subscribeon.html)
    // 3. Creates a reference to the disposable Observable
    open fun execute(observer: DisposableObserver<T>, params: Params? = null){
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribeWith(observer))
    }

    // Method that adds a disposable to the list of disposables.
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    // A method that clears out the disposable observers we've created.
    // This should be done when an Android activity closes
    fun dispose() {
        disposables.dispose()
    }
}