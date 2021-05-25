package com.medialink.repoandlivesubmission.data.source.remote.entity.movie


import com.google.gson.annotations.SerializedName

data class ReviewRespon(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Review>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)