package com.medialink.repoandlivesubmission.data.source.remote.entity


import com.google.gson.annotations.SerializedName

data class VideoRespon(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<Video>?
)