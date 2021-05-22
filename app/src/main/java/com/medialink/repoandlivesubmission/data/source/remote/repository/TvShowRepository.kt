package com.medialink.repoandlivesubmission.data.source.remote.repository

import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.utils.AppConfig

class TvShowRepository(private val apiService: ApiService) : IRepository {


    override suspend fun getList(page: Int): List<Detail> {
        val listTv = apiService.getListTv(ApiConfig.LANGUAGE, page)
        val listResult = mutableListOf<Detail>()
        listTv.results?.let {
            for (item in it) {
                listResult.add(
                    Detail(
                        AppConfig.TYPE_TV_SHOW,
                        item.backdropPath,
                        item.genres,
                        item.homepage,
                        item.id,
                        item.originalLanguage,
                        item.originalName,
                        item.overview,
                        item.popularity,
                        String.format(ApiConfig.POSTER_PATH, item.posterPath),
                        item.firstAirDate,
                        item.status,
                        item.name,
                        item.voteAverage,
                        item.voteCount
                    )
                )
            }
        }

        return listResult
    }

    override suspend fun getDetail(id: Int): Detail {
        val tvShow = apiService.getTvShow(id, ApiConfig.LANGUAGE)

        return Detail(
            AppConfig.TYPE_MOVIE,
            tvShow.backdropPath,
            tvShow.genres,
            tvShow.homepage,
            tvShow.id,
            tvShow.originalLanguage,
            tvShow.originalName,
            tvShow.overview,
            tvShow.popularity,
            String.format(ApiConfig.POSTER_PATH, tvShow.posterPath),
            tvShow.firstAirDate,
            tvShow.status,
            tvShow.name,
            tvShow.voteAverage,
            tvShow.voteCount
        )
    }

}
