package com.example.moviesapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg manyMovies: Movie)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies(): Flow<List<Movie>>


//    @Query("DELETE FROM movies_table")
//    suspend fun deleteAllMovies()


}