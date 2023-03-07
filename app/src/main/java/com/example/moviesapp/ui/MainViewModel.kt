package com.example.moviesapp.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.DataSource
import com.example.moviesapp.base.BaseViewModel
import com.example.moviesapp.model.Movie
import kotlinx.coroutines.launch
import com.example.moviesapp.data.database.Result
import kotlinx.coroutines.flow.Flow

class MainViewModel(

    private val mainRepo: DataSource,
    application: Application
) : BaseViewModel(application) {

    private val _topRatedMoviesList = MutableLiveData<List<Movie>>()
    val topRatedMoviesList: LiveData<List<Movie>>
        get() = _topRatedMoviesList

    init {
        showLoading.value = true
        viewModelScope.launch {
            try {
                mainRepo.refreshData()
                getTopRatedMovies()
                showLoading.postValue(false)
            } catch (e: Exception) {
                Log.e("MainViewModel", e.message.toString())
                Toast.makeText(application, "Failure: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getTopRatedMovies() {
        showLoading.value = true

        viewModelScope.launch {
            val result = mainRepo.getTopRatedMovies()
            showLoading.postValue(false)

            when (result) {
                is Result.Success<*> -> {
                    val data = result.data as Flow<List<Movie>>
                    data.collect {
                        _topRatedMoviesList.value = it
                    }
                }
                is Result.Error ->
                    showSnackBar.value = result.message

            }

        }
    }

}