/**
 * I've made reference to the PostExecutionThread interface.
 * This interface lets me abstract out the thread in which our application
 * operations are to perform. Since these various classes need this interface,
 * I'll need to create an implementation in the UI layer, so that when I create
 * the dependency injection logic I'll be able to say use this implementation wherever a
 * postExecutionThread is required.
 *
 * The Domain layer is a Kotlin module and knows nothings of the Android framework. The
 * Domain layer is the main module that requests a PostExecutionThread.  This abstraction
 * allows me to keep all references to the Android framework out of the Domain layer/module.
 *
 */
package com.loc8r.mobile_ui

import com.loc8r.domain.interfaces.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UiThread @Inject constructor(): PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}