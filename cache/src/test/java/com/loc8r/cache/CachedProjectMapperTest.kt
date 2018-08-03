package com.loc8r.cache

import com.loc8r.cache.model.CachedProject
import com.loc8r.cache.utils.ProjectDataFactory
import com.loc8r.data.models.ProjectEntity
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class CachedProjectMapperTest {

    private val mapper = CachedProjectMapper()

    private fun assertDataEquals(model: CachedProject, entity: ProjectEntity){
        assertEquals(model.id, entity.id)
        assertEquals(model.dateCreated, entity.dateCreated)
        assertEquals(model.fullName, entity.fullName)
        assertEquals(model.isBookmarked, entity.isBookmarked)
        assertEquals(model.name, entity.name)
        assertEquals(model.ownerAvatar, entity.ownerAvatar)
        assertEquals(model.ownerName, entity.ownerName)
        assertEquals(model.starCount, entity.starCount)
    }

    @Before
    fun setUp() {
    }

    @Test
    fun mapFromCacheMapsDataCorrectly() {
        val model = ProjectDataFactory.makeCachedProject()
        val entity = mapper.mapFromCache(model)

        assertDataEquals(model,entity)
    }

    @Test
    fun mapToCacheMapsDataCorrectly() {
        val entity = ProjectDataFactory.makeProjectEntity()
        val model = mapper.mapToCache(entity)

        assertDataEquals(model,entity)
    }
}