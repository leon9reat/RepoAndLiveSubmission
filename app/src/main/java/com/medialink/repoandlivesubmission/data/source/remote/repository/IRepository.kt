package com.medialink.repoandlivesubmission.data.source.remote.repository

import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.entity.ReviewRespon
import com.medialink.repoandlivesubmission.data.source.remote.entity.VideoRespon
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Movie
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.TvShow

interface IRepository {
    suspend fun getList(page: Int): List<Detail>
    suspend fun getDetail(id: Int): Detail
}

interface IMovieRepository : IRepository {
    suspend fun getMovie(id: Int, language: String): Movie
    suspend fun getMovieReview(id: Int, language: String, page: Int): ReviewRespon
    suspend fun getMovieVideo(id: Int, language: String): VideoRespon
}

interface ITvRepository : IRepository {
    suspend fun getTvShow(id: Int, language: String): TvShow
    suspend fun getTvReview(id: Int, language: String, page: Int): ReviewRespon
    suspend fun getTvVideo(id: Int, language: String): VideoRespon
}