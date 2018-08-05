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

    fun makeProjectEntityList(count: Int): List<ProjectEntity> {
        val projects = mutableListOf<ProjectEntity>()
        repeat(count){
            projects.add(makeProjectEntity())
        }
        return projects
    }

    fun makeBookmarkedCachedProject(): CachedProject {
        return CachedProject(DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                true)
    }

    fun makeBookmarkedProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                true)
    }

    fun makeNonBookmarkedProjectEntity(): ProjectEntity {
        return ProjectEntity(DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid(),
                false)
    }


}