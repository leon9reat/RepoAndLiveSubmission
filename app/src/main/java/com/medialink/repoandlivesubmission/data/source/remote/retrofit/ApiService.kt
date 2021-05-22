package com.medialink.repoandlivesubmission.data.source.remote.retrofit

import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.ListMovies
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.ListTv
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Movie
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.TvShow
import retrofit2.Call
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
}