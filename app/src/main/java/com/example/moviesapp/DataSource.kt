package com.example.moviesapp

import com.example.moviesapp.data.database.Result
import com.example.moviesapp.model.Movie
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun getTopRatedMovies(): Result<Flow<List<Movie>>>
    suspend fun refreshData()

}