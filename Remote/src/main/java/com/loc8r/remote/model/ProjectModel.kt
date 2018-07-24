package com.loc8r.remote.model

import com.google.gson.annotations.SerializedName

class ProjectModel(val name: String,
                   @SerializedName("full_name") val fullName: String,
                   @SerializedName("stargazer_count") val starCount: Int,
                   @SerializedName("created_at") val dateCreated: String,
                   val owner: OwnerModel) {
}