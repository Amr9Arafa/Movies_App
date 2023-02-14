package com.example.moviesapp.api

import com.example.moviesapp.model.Movie
import com.example.moviesapp.model.MoviesResponse
import com.example.moviesapp.utils.Constants.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("3/movie/top_rated")
    fun getTopRatedMoviesAsync(@Query("api_key") apiKey: String = API_KEY): Deferred<MoviesResponse>


}