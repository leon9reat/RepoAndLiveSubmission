package com.medialink.repoandlivesubmission.data.source.remote.repository

import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.utils.AppConfig

open class MovieRepository(private val apiService: ApiService) : IRepository {

    override suspend fun getList(page: Int): List<Detail> {
        val listMovie = apiService.getListMovies(
            ApiConfig.LANGUAGE, page
        )
        val listResult = mutableListOf<Detail>()

        listMovie.results?.let {
            for (item in it) {
                listResult.add(
                    Detail(
                        AppConfig.TYPE_MOVIE,
                        item.backdropPath,
                        item.genres,
                        item.homepage,
                        item.id,
                        item.originalLanguage,
                        item.originalTitle,
                        item.overview,
                        item.popularity,
                        String.format(ApiConfig.POSTER_PATH, item.posterPath),
                        item.releaseDate,
                        item.status,
                        item.title,
                        item.voteAverage,
                        item.voteCount
                    )
                )

            }
        }
        return listResult
    }

    override suspend fun getDetail(id: Int): Detail {
        val movie = apiService.getMovie(
            id, ApiConfig.LANGUAGE
        )

        return Detail(AppConfig.TYPE_MOVIE,
            movie.backdropPath,
            movie.genres,
            movie.homepage,
            movie.id,
            movie.originalLanguage,
            movie.originalTitle,
            movie.overview,
            movie.popularity,
            String.format(ApiConfig.POSTER_PATH, movie.posterPath),
            movie.releaseDate,
            movie.status,
            movie.title,
            movie.voteAverage,
            movie.voteCount
        )
    }

}