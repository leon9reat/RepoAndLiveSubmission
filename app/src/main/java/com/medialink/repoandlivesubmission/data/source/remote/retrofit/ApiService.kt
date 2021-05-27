package com.medialink.repoandlivesubmission.data.source.remote.retrofit

import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.ListMovies
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.ListTv
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Movie
import com.medialink.repoandlivesubmission.data.source.remote.entity.ReviewRespon
import com.medialink.repoandlivesubmission.data.source.remote.entity.VideoRespon
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.TvShow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tv/popular")
    suspend fun getListTv(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ListTv

    @GET("tv/{id}")
    suspend fun getTvShow(
        @Path("id") id: Int,
        @Query("language") language: String
    ): TvShow

    @GET("movie/popular")
    suspend fun getListMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): ListMovies

    @GET("movie/{id}")
    suspend fun getMovie(
        @Path("id") id: Int,
        @Query("language") language: String
    ): Movie

    @GET("movie/{id}/reviews")
    suspend fun  getMovieReview(
        @Path("id") id: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ReviewRespon

    @GET("movie/{id}/videos")
    suspend fun getMovieVideo(
        @Path("id") id: Int,
        @Query("language") language: String
    ): VideoRespon

    @GET("tv/{id}/reviews")
    suspend fun  getTvReview(
        @Path("id") id: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ReviewRespon

    @GET("tv/{id}/videos")
    suspend fun getTvVideo(
        @Path("id") id: Int,
        @Query("language") language: String
    ): VideoRespon
}