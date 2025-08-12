package com.psm.myfilms

import android.app.Application
import androidx.room.Room
import com.psm.myfilms.data.data_sources.database.MoviesDatabase

class App : Application() {

    lateinit var db: MoviesDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            MoviesDatabase::class.java,
            "movies-db"
        ).build()
    }

}