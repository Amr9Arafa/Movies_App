package com.example.moviesapp

import com.example.moviesapp.model.Movie
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getTopRatedMovies(): com.example.moviesapp.database.Result<Flow<List<Movie>>>
    suspend fun refreshData()

}