/**
 * Our Projects model, used by Room to build a table because of the Entity annotation.
 * for info on Kotlin data classes see: @see https://kotlinlang.org/docs/reference/data-classes.html
 */

package com.loc8r.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.loc8r.cache.ProjectConstants

@Entity(tableName = ProjectConstants.TABLE_NAME)
data class CachedProject (
    @PrimaryKey
    @ColumnInfo(name = ProjectConstants.COLUMN_PROJECT_ID)
    var id: String,
    var name: String,
    var fullName: String,
    var starCount: String,
    var dateCreated: String,
    var ownerName: String,
    var ownerAvatar: String,
    @ColumnInfo(name = ProjectConstants.COLUMN_IS_BM)
    var isBookmarked: Boolean
){
}