package com.medialink.repoandlivesubmission.ui.detail.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Movie
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Review
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Video
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.utils.EspressoIdlingResource
import com.medialink.repoandlivesubmission.utils.Resource
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val apiService: ApiService) : ViewModel() {

    private val _movie = MutableLiveData<Resource<Movie>>()

    private val _review = MutableLiveData<Resource<List<Review>>>()

    private val _video = MutableLiveData<Resource<List<Video>>>()

    fun fetchMovie(id: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _movie.postValue(Resource.loading(null))
            try {
                val movie = apiService.getMovie(id, ApiConfig.LANGUAGE)
                _movie.postValue(Resource.success(movie))
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                _movie.postValue(Resource.error(null, message = e.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun fetchReview(id: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _review.postValue(Resource.loading(null))
            try {
                val review = apiService.getMovieReview(id, ApiConfig.LANGUAGE, 1)
                _review.postValue(Resource.success(review.results) as Resource<List<Review>>?)
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                _review.postValue(Resource.error(null, message = e.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun fetchVideo(id: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _video.postValue(Resource.loading(null))
            try {
                val video = apiService.getMovieVideo(id, ApiConfig.LANGUAGE)
                _video.postValue(Resource.success(video.results) as Resource<List<Video>>?)
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                _video.postValue(Resource.error(null, message = e.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getMovie(): LiveData<Resource<Movie>> {
        return _movie
    }

    fun getReview(): LiveData<Resource<List<Review>>> {
        return _review
    }

    fun getVideo(): LiveData<Resource<List<Video>>> {
        return _video
    }

}