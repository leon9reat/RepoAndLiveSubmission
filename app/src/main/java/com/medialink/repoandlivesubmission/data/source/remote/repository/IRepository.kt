package com.medialink.repoandlivesubmission.data.source.remote.repository

import com.medialink.repoandlivesubmission.data.source.local.entity.Detail

interface IRepository {
    suspend fun getList(page: Int): List<Detail>
    suspend fun getDetail(id: Int): Detail
}