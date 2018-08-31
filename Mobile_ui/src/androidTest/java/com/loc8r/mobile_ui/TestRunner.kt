/**
 * How will Espresso know to use our TestApplication instead of our real application when running
 * instrumented tests?  The answer is that I'm going to write a customer Test runner, this test
 * runner.
 */
package com.loc8r.mobile_ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.runner.AndroidJUnitRunner
import com.loc8r.mobile_ui.di.TestApplication
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class TestRunner: AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)
        // The Trampoline scheduler tells our test cases to run in order
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context)
            : Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}