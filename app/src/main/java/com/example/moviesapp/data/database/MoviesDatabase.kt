package com.example.moviesapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDataBase : RoomDatabase() {

    abstract val moviesDatabaseDao: MoviesDataBaseDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDataBase? = null

        fun getInstance(context: Context): MoviesDataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDataBase::class.java,
                        "movies_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}