package com.psm.myfilms.data.data_sources

import com.psm.myfilms.domain.Location


interface LocationDataSource {
    suspend fun getLastLocation(): Location?
}