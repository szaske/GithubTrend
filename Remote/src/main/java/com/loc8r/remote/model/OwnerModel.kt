package com.loc8r.remote.model

import com.google.gson.annotations.SerializedName

class OwnerModel(@SerializedName("login") val ownerName: String,
                 @SerializedName("avatar_url") val ownerAvater: String) {
}