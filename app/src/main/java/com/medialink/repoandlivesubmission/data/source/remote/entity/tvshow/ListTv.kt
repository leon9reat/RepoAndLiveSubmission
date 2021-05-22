package com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow


import com.google.gson.annotations.SerializedName

data class ListTv(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<TvShow>?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?
)