package com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow


import com.google.gson.annotations.SerializedName

data class Network(
    @SerializedName("name")
    val name: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)