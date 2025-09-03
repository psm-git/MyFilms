package com.psm.myfilms.data.data_sources

const val DEFAULT_REGION = "US"

interface RegionDataSource {
    suspend fun findLastRegion(): String
}