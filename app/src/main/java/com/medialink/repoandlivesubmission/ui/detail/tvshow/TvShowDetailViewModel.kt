package com.medialink.repoandlivesubmission.ui.detail.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.entity.Review
import com.medialink.repoandlivesubmission.data.source.remote.entity.Video
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.TvShow
import com.medialink.repoandlivesubmission.data.source.remote.repository.ITvRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.utils.EspressoIdlingResource
import com.medialink.repoandlivesubmission.utils.Resource
import kotlinx.coroutines.launch

class TvShowDetailViewModel(private val tvRepository: ITvRepository): ViewModel() {

    private val _tvShow = MutableLiveData<Resource<TvShow>>()

    private val _review = MutableLiveData<Resource<List<Review>>>()

    private val _video = MutableLiveData<Resource<List<Video>>>()

    fun fetchTvShow(id: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _tvShow.postValue(Resource.loading(null))
            try {
                val tvShow = tvRepository.getTvShow(id, ApiConfig.LANGUAGE)
                _tvShow.postValue(Resource.success(tvShow))
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                _tvShow.postValue(Resource.error(null, message = e.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun fetchTvReview(id: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _review.postValue(Resource.loading(null))
            try {
                val review = tvRepository.getTvReview(id, ApiConfig.LANGUAGE, 1)
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
                val video = tvRepository.getTvVideo(id, ApiConfig.LANGUAGE)
                _video.postValue(Resource.success(video.results) as Resource<List<Video>>?)
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                _video.postValue(Resource.error(null, message = e.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getTvShow(): LiveData<Resource<TvShow>> {
        return _tvShow
    }

    fun getReview(): LiveData<Resource<List<Review>>> {
        return _review
    }

    fun getVideo(): LiveData<Resource<List<Video>>> {
        return _video
    }

}