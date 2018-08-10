package com.loc8r.presentation.utils

import com.loc8r.domain.models.Project
import com.loc8r.presentation.models.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomBoolean())
    }

    fun makeProject(): Project {
        return Project(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomBoolean())
    }

    fun makeProjectViewList(count: Int): List<ProjectView>{
        var projects = mutableListOf<ProjectView>()
        repeat(count){
            projects.add(makeProjectView())
        }
        return projects
    }

    fun makeProjectList(count: Int): List<Project>{
        var projects = mutableListOf<Project>()
        repeat(count){
            projects.add(makeProject())
        }
        return projects
    }
}