package com.medialink.repoandlivesubmission.ui.fragment

import com.medialink.repoandlivesubmission.data.source.local.entity.Detail

interface IBaseFragment {
    fun onItemClick(detail: Detail)
    fun onShareClick(detail: Detail)
    fun onFavoriteClick(detail: Detail)
}