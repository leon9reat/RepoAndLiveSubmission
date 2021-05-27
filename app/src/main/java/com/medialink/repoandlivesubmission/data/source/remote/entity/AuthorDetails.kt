package com.medialink.repoandlivesubmission.data.source.remote.entity


import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    @SerializedName("name")
    val name: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Double?
)