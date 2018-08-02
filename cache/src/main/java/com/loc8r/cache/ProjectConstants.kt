package com.loc8r.cache

object ProjectConstants {
    const val TABLE_NAME = "projects"
    const val COLUMN_PROJECT_ID = "project_id"
    const val COLUMN_IS_BM = "is_bookmarked"
    const val QUERY_PROJECTS = "SELECT * FROM $TABLE_NAME"
    const val DELETE_PROJECTS = "DELETE * FROM $TABLE_NAME"
    const val QUERY_BOOKMARKED_PROJECTS = "SELECT * FROM $TABLE_NAME " +
            "WHERE $COLUMN_IS_BM = 1"
    const val QUERY_UPDATE_BOOKMARK_STATUS = "UPDATE $TABLE_NAME " +
            "SET $COLUMN_IS_BM = :isBookmarked WHERE " +
            "$COLUMN_PROJECT_ID = :projectId"
}