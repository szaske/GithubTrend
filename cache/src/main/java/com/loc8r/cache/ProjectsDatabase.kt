/**
 * Room requires that this class be abstract.
 * Version allows us to alter the data structure if needed.
 * We're also required to build a
 */
package com.loc8r.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.loc8r.cache.model.CachedProject
import javax.inject.Inject

@Database(entities = arrayOf(CachedProject::class), version = 1)
abstract class ProjectsDatabase @Inject constructor(): RoomDatabase(){

    abstract fun cachedProjectsDao(): CachedProjectsDao

    //We make it a Singleton
    private var INSTANCE: ProjectsDatabase? = null

    // Any is the Kotlin super class.  I think this just makes a generic object...which as an
    // Any has equals and hashcode
    private val lock = Any()

    fun getInstance(context: Context): ProjectsDatabase {
        if(INSTANCE == null){
            synchronized(lock){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ProjectsDatabase::class.java, "projects.db")
                            .build()
                }
                return INSTANCE as ProjectsDatabase
            }
        }
        return INSTANCE as ProjectsDatabase
    }

}