package com.medialink.repoandlivesubmission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medialink.repoandlivesubmission.data.source.remote.repository.MovieRepository
import com.medialink.repoandlivesubmission.data.source.remote.repository.TvShowRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.ui.detail.movie.MovieDetailViewModel
import com.medialink.repoandlivesubmission.ui.detail.tvshow.TvShowDetailViewModel

class ViewModelFactory(private val apiService: ApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            val repository = MovieRepository(apiService)
            return MovieDetailViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(TvShowDetailViewModel::class.java)) {
            val repository = TvShowRepository(apiService)
            return TvShowDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}