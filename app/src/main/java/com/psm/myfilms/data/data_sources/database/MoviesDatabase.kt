package com.psm.myfilms.data.data_sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.psm.myfilms.data.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}