/**
 *  See: https://antonioleiva.com/objects-kotlin/
 *
 *  Objects are Java singletons.  This is a factory that will
 *  create dummy test data for my unit tests
 *
 */

package com.loc8r.domain.interactors.factory

import com.loc8r.domain.models.Project
import java.util.*

// Object makes this a singleton, so it does not need to be
// instantiated to be used.
object ProjectDataFactory {

    fun randomUUid(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makeProject(): Project {
        return Project(randomUUid(),randomUUid(),randomUUid(),
                randomUUid(),randomUUid(),randomUUid(),
                randomUUid(), randomBoolean())
    }

    fun makeProjectList(count: Int): List<Project> {
        val projects = mutableListOf<Project>()
        repeat(count){
            projects.add(makeProject())
        }
        return projects
    }

}