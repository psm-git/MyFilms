package com.psm.myfilms.di

import com.psm.myfilms.data.data_sources.LocationDataSource
import com.psm.myfilms.data.data_sources.MoviesLocalDataSource
import com.psm.myfilms.data.data_sources.MoviesRemoteDataSource
import com.psm.myfilms.data.data_sources.RegionDataSource
import com.psm.myfilms.framework.LocationPlayServicesDataSource
import com.psm.myfilms.framework.RegionGeocoderDataSource
import com.psm.myfilms.framework.database.MoviesRoomDataSource
import com.psm.myfilms.framework.remote.MoviesRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindMoviesRemoteDataSource(impl: MoviesRetrofitDataSource): MoviesRemoteDataSource

    @Binds
    abstract fun bindMoviesLocalDataSource(impl: MoviesRoomDataSource): MoviesLocalDataSource

    @Binds
    abstract fun bindLocationDataSource(impl: LocationPlayServicesDataSource): LocationDataSource

    @Binds
    abstract fun bindRegionDataSource(impl: RegionGeocoderDataSource): RegionDataSource

}