package com.example.moviesapp.repository

import com.example.moviesapp.DataSource
import com.example.moviesapp.data.api.RetrofitBuilder
import com.example.moviesapp.data.database.MoviesDataBase
import com.example.moviesapp.model.Movie
import com.example.moviesapp.model.asDatabaseModel
import com.example.moviesapp.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.example.moviesapp.data.database.Result

class MoviesRepository(private val database: MoviesDataBase) : DataSource {


    override suspend fun refreshData() {
        wrapEspressoIdlingResource {
            withContext(Dispatchers.IO) {
                val response = RetrofitBuilder.retrofitService.getTopRatedMoviesAsync().await()
                database.moviesDatabaseDao.insertAll(*response.results.asDatabaseModel())
            }

        }
    }


    override suspend fun getTopRatedMovies(): Result<Flow<List<Movie>>> =
        withContext(Dispatchers.IO) {
            wrapEspressoIdlingResource {
                return@withContext try {
                    Result.Success(database.moviesDatabaseDao.getAllMovies())
                } catch (ex: Exception) {
                    Result.Error(ex.localizedMessage)
                }
            }
        }


}