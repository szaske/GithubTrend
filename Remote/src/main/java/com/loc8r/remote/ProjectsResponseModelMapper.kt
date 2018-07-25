package com.loc8r.remote

import com.loc8r.data.models.ProjectEntity
import com.loc8r.remote.interfaces.ModelMapper
import com.loc8r.remote.models.ProjectModel

class ProjectsResponseModelMapper: ModelMapper<ProjectModel,ProjectEntity> {
    override fun mapFromModel(model: ProjectModel): ProjectEntity {
        return ProjectEntity(model.id,
                model.name,
                model.fullName,
                model.starCount.toString(),
                model.dateCreated,
                model.owner.ownerName,
                model.owner.ownerAvater)
    }

}