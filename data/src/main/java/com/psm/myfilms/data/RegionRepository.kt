package com.psm.myfilms.data

import com.psm.myfilms.data.data_sources.RegionDataSource


class RegionRepository(private val regionDataSource: RegionDataSource) {

    suspend fun findLastRegion(): String = regionDataSource.findLastRegion()

}