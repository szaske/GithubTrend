package com.loc8r.mobile_ui

import com.loc8r.mobile_ui.models.Project
import com.loc8r.mobile_ui.utils.ProjectFactory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class ProjectViewMapperTest {

    private val projectViewMapper = ProjectViewMapper()

    @Test
    fun mapToViewMapsDataCorrectly() {
        val project = ProjectFactory.makeProjectView()
        val projectForUi = projectViewMapper.mapToView(project)

        assertEquals(project.id, projectForUi.id)
        assertEquals(project.name, projectForUi.name)
        assertEquals(project.fullName, projectForUi.fullName)
        assertEquals(project.starCount, projectForUi.starCount)
        assertEquals(project.dateCreated, projectForUi.dateCreated)
        assertEquals(project.ownerName, projectForUi.ownerName)
        assertEquals(project.ownerAvatar, projectForUi.ownerAvatar)
        assertEquals(project.isBookmarked, projectForUi.isBookmarked)
    }

    @Test
    fun mapToViewReturnsCorrectClass() {
        val project = ProjectFactory.makeProjectView()
        val projectForUi = projectViewMapper.mapToView(project)
        assertSame<Class<Project>>(Project::class.java,projectForUi.javaClass)
    }
}