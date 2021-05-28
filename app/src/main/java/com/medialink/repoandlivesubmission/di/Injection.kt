package com.medialink.repoandlivesubmission.di

import com.medialink.repoandlivesubmission.data.source.remote.repository.IRepository
import com.medialink.repoandlivesubmission.data.source.remote.repository.MovieRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService

object Injection {
    fun provideRepository(apiService: ApiService): IRepository {
        return MovieRepository(apiService)
    }
}