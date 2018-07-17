/**
 *  We don't want a reference to RxAndroid in out domain module,
 *  as this is an implementation detail and would break the
 *  separation of concerns.
 *
 *  So I'm creating this interface to abstract away the RxAndroid implementation
 *  Scheduler is of type RxJava...which is fine to reference from the domain module
 *
 */
package com.loc8r.domain.interfaces

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}