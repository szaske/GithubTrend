/**
 * Unit tests to test the ProjectMapper class. The Project mapper
 * converts Projects to ProjectEntities and vice versa
 */
package com.loc8r.data

import com.loc8r.data.models.ProjectEntity
import com.loc8r.domain.models.Project
import org.junit.Test
import utils.ProjectFactory
import kotlin.test.assertEquals

// @RunWith(JUnit4::class)
// I don't believe  Runwith is needed
class ProjectsMapperTest {
    //create a new ProjectMapper
    private val mapper = ProjectMapper()

    // A private test that compares entity and model
    // across all members
    private fun assertEqualData(entity: ProjectEntity,
                                model: Project) {
        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.fullName, model.fullName)
        assertEquals(entity.starCount, model.starCount)
        assertEquals(entity.dateCreated, model.dateCreated)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
        assertEquals(entity.isBookmarked, model.isBookmarked)
    }


    @Test
    fun mapFromEntityMapsData(){
        val entity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(entity)
        assertEqualData(entity,model)
    }

    @Test
    fun mapToEntityMapsData() {
        val model = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(model)

        assertEqualData(entity, model)
    }
}