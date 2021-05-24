package com.medialink.repoandlivesubmission.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.medialink.repoandlivesubmission.data.source.remote.repository.IRepository
import com.medialink.repoandlivesubmission.ui.fragment.FragmentViewModel

object Factory {

    fun getFactory(mRepository: IRepository) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return FragmentViewModel(mRepository) as T
            }
        }

}