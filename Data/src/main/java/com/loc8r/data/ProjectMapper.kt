/**
 *  A Mapper class that helps convert Domain Projects to Data Projects and back again.
 */
package com.loc8r.data

import com.loc8r.data.interfaces.EntityMapper
import com.loc8r.data.models.ProjectEntity
import com.loc8r.domain.models.Project
import javax.inject.Inject

// Needs to be open so we can mock it in Mockito
open class ProjectMapper @Inject constructor(): EntityMapper<ProjectEntity, Project> {
    override fun mapFromEntity(entity: ProjectEntity): Project {
        return Project(entity.id,
                entity.name,
                entity.fullName,
                entity.starCount,
                entity.dateCreated,
                entity.ownerName,
                entity.ownerAvatar,
                entity.isBookmarked)
    }

    override fun mapToEntity(domain: Project): ProjectEntity {
        return ProjectEntity(domain.id, domain.name, domain.fullName,
                domain.starCount, domain.dateCreated, domain.ownerName,
                domain.ownerAvatar, domain.isBookmarked)
    }
}