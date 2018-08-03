package com.loc8r.cache.utils

import com.loc8r.cache.model.CachedProject
import com.loc8r.data.models.ProjectEntity

object ProjectDataFactory {
    fun makeCachedProject(): CachedProject {
        return CachedProject(DataFactory.randomUuid(),DataFactory.randomUuid(),
                DataFactory.randomUuid(),DataFactory.randomUuid(),DataFactory.randomUuid(),
                DataFactory.randomUuid(),DataFactory.randomUuid(),DataFactory.randomBoolean())
    }

    fun makeProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomUuid(),DataFactory.randomUuid(),
                DataFactory.randomUuid(),DataFactory.randomUuid(),DataFactory.randomUuid(),
                DataFactory.randomUuid(),DataFactory.randomUuid(),DataFactory.randomBoolean())
    }

}