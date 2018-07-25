package com.loc8r.remote.utils

import com.loc8r.data.models.ProjectEntity
import com.loc8r.remote.models.OwnerModel
import com.loc8r.remote.models.ProjectModel
import com.loc8r.remote.models.ProjectsResponseModel

object ProjectsDataFactory {
    fun makeOwner(): OwnerModel {
        return OwnerModel(DataFactory.randomUuid(),DataFactory.randomUuid())
    }

    fun makeProject(): ProjectModel {
        return ProjectModel(DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomInt(),
                DataFactory.randomUuid(),
                makeOwner())
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomUuid(),
                DataFactory.randomBoolean())
    }

    fun makeProjectsResponse(): ProjectsResponseModel {
        return ProjectsResponseModel(listOf(makeProject(), makeProject()))
    }
}