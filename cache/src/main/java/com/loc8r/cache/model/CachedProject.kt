/**
 * Our Projects model, used by Room to build a table because of the Entity annotation.
 * for info on Kotlin data classes see: @see https://kotlinlang.org/docs/reference/data-classes.html
 */

package com.loc8r.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.loc8r.cache.ProjectConstants

@Entity(tableName = ProjectConstants.TABLE_NAME)
data class CachedProject (
    @PrimaryKey
    var id: String,
    var name: String,
    var fullName: String,
    var starCount: String,
    var dateCreated: String,
    var ownerName: String,
    var ownerAvatar: String,
    var isBookmarked: Boolean
){
}