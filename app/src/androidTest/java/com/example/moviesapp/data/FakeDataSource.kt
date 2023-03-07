package com.example.moviesapp.data

import com.example.moviesapp.DataSource
import com.example.moviesapp.data.database.Result
import com.example.moviesapp.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataSource : DataSource {

     var moviesListL: ArrayList<Movie> =arrayListOf<Movie>()

    var errorMsg: String = ""
    var shouldReturnError = false


    override suspend fun getTopRatedMovies(): Result<Flow<List<Movie>>> {
        try {
            if (!shouldReturnError) {
                if (errorMsg == "") {
                    return Result.Success(flowOf(moviesListL))
                } else {
                    return Result.Error(errorMsg)
                }
            } else
                return Result.Error("Failed To load data")


        } catch (e: Exception) {
            return Result.Error(e.localizedMessage)
        }
    }


    override suspend fun refreshData() {
        val movieOne = Movie(
            1,
            "title1",
            "poster_path_1",
            "overview1"
        )
        val movieTwo = Movie(
            2,
            "title2",
            "poster_path_2",
            "overview2"
        )
        moviesListL.add(movieOne)
        moviesListL.add(movieTwo)
    }
}