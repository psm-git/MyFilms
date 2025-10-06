package com.psm.myfilms.framework

import android.location.Geocoder
import com.psm.myfilms.data.data_sources.DEFAULT_REGION
import com.psm.myfilms.data.data_sources.LocationDataSource
import com.psm.myfilms.data.data_sources.RegionDataSource
import com.psm.myfilms.domain.Location
import com.psm.myfilms.ui.common.getFromLocationCompat
import javax.inject.Inject

class RegionGeocoderDataSource @Inject constructor(
    private val geocoder: Geocoder,
    private val locationDataSource: LocationDataSource
) : RegionDataSource {

    override suspend fun findLastRegion(): String =
        locationDataSource.getLastLocation()?.toRegion() ?: DEFAULT_REGION

    private suspend fun Location.toRegion(): String {
        val addresses = geocoder.getFromLocationCompat(latitude, longitude, 1)
        val region = addresses.firstOrNull()?.countryCode
        return region ?: DEFAULT_REGION
    }

}