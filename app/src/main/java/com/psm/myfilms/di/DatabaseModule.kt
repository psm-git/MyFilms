package com.psm.myfilms.di

import android.app.Application
import androidx.room.Room
import com.psm.myfilms.framework.database.MoviesDao
import com.psm.myfilms.framework.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDatabase = Room.databaseBuilder(
        app,
        MoviesDatabase::class.java,
        "movies-db"
    ).build()

    @Provides
    fun provideMoviesDao(db: MoviesDatabase): MoviesDao = db.moviesDao()

}