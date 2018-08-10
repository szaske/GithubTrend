package com.loc8r.presentation

import com.loc8r.domain.models.Project
import com.loc8r.presentation.interfaces.PresentationMapper
import com.loc8r.presentation.models.ProjectView
import javax.inject.Inject

open class ProjectViewMapper @Inject constructor(): PresentationMapper<ProjectView, Project> {

    override fun mapToView(type: Project): ProjectView {
        return ProjectView(type.id,type.name,type.fullName,type.starCount,
                type.dateCreated,type.ownerName,type.ownerAvatar,type.isBookmarked)
    }
}