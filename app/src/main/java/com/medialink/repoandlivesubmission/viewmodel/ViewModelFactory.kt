package com.medialink.repoandlivesubmission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.ui.detail.movie.MovieDetailViewModel

class ViewModelFactory(private val apiService: ApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}