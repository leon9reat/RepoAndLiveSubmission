package com.medialink.repoandlivesubmission.data.source.local.entity

import com.medialink.repoandlivesubmission.data.source.remote.entity.Genre

data class Detail(
    val idType: Int, // 1 MOVIE, 2 TV SHOW
    val backdropPath: String? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val date: String? = null,
    val status: String? = null,
    val title: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)