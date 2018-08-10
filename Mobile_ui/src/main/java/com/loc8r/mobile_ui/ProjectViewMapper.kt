package com.loc8r.mobile_ui

import com.loc8r.mobile_ui.interfaces.ViewMapper
import com.loc8r.mobile_ui.models.Project
import com.loc8r.presentation.models.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor(): ViewMapper<ProjectView, Project> {
    override fun mapToView(presentation: ProjectView): Project {
        return Project(presentation.id, presentation.name,presentation.fullName, presentation.starCount,
                presentation.dateCreated,presentation.ownerName,presentation.ownerAvatar,presentation.isBookmarked)
    }
}