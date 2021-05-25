package com.medialink.repoandlivesubmission.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.repository.IRepository
import com.medialink.repoandlivesubmission.utils.EspressoIdlingResource
import com.medialink.repoandlivesubmission.utils.Resource
import kotlinx.coroutines.launch

class FragmentViewModel(private val repository: IRepository) : ViewModel() {

    private val _listData = MutableLiveData<Resource<List<Detail>>>()

    init {
        fetchListData(1)
    }

    fun fetchListData(page: Int) {
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            _listData.postValue(Resource.loading(null))
            try {
                val listFromApi = repository.getList(page)
                _listData.postValue(Resource.success(listFromApi))
                EspressoIdlingResource.decrement()
            } catch (e: java.lang.Exception) {
                _listData.postValue(Resource.error(null, message = e.message ?: "Error Occurred!"))
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getList(): LiveData<Resource<List<Detail>>> {
        return _listData
    }
}