package com.psm.myfilms.framework

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.psm.myfilms.data.data_sources.LocationDataSource
import com.psm.myfilms.domain.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import android.location.Location as PlayServicesLocation

class LocationPlayServicesDataSource(private val fusedLocationClient: FusedLocationProviderClient) :
    LocationDataSource {

    override suspend fun getLastLocation() = fusedLocationClient.lastLocation()

    @SuppressLint("MissingPermission")
    private suspend fun FusedLocationProviderClient.lastLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            lastLocation.addOnSuccessListener {
                continuation.resume(it.toDomainLocation())
            }.addOnFailureListener {
                continuation.resume(null)
            }
        }
    }

    private fun PlayServicesLocation.toDomainLocation() = Location(latitude, longitude)

}