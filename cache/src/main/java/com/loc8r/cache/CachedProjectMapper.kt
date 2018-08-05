/**
 * This mapper class helps us convert data models from Cache type to Data type.  Although these
 * models are extremely similar, we do this to enforce separation of concerns
 */
package com.loc8r.cache

import com.loc8r.cache.model.CachedProject
import com.loc8r.data.models.ProjectEntity
import javax.inject.Inject

class CachedProjectMapper @Inject constructor(): CacheMapper<CachedProject, ProjectEntity> {
    override fun mapFromCache(type: CachedProject): ProjectEntity {
        return ProjectEntity(type.id, type.name, type.fullName, type.starCount,
                type.dateCreated, type.ownerName, type.ownerAvatar, type.isBookmarked)
    }

    override fun mapToCache(type: ProjectEntity): CachedProject {
        return CachedProject(type.id, type.name, type.fullName, type.starCount,
                type.dateCreated, type.ownerName, type.ownerAvatar, type.isBookmarked)
    }
}