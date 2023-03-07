package com.example.moviesapp.ui

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviesapp.MainCoroutineRule
import com.example.moviesapp.data.FakeDataSource
import com.example.moviesapp.data.database.Result
import com.example.moviesapp.model.Movie
import com.example.moviesapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var appContext: Application
    private lateinit var dataSource: FakeDataSource
    private lateinit var mainViewModel: MainViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun init() {
        appContext = ApplicationProvider.getApplicationContext()
        dataSource = FakeDataSource()
        mainViewModel = MainViewModel(dataSource, appContext)

    }

    @Test
    fun get_all_movies_assertion() {
        mainCoroutineRule.runBlockingTest {
            mainViewModel.getTopRatedMovies()
            val result = dataSource.getTopRatedMovies()

            when (result) {
                is Result.Success<*> -> {
                    val data = result.data as Flow<List<Movie>>
                    data.collect {
                        assertEquals(
                            it,
                            mainViewModel.topRatedMoviesList.value
                        )
                    }
                }
                is Result.Error ->
                    MatcherAssert.assertThat(
                        mainViewModel.showSnackBar.getOrAwaitValue(), CoreMatchers.equalTo("Failed")
                    )


            }
        }
    }

    @Test
    fun get_movies_show_loading() = mainCoroutineRule.runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        mainViewModel.getTopRatedMovies()
        MatcherAssert.assertThat(
            mainViewModel.showLoading.getOrAwaitValue(),
            CoreMatchers.equalTo(true)
        )
        mainCoroutineRule.resumeDispatcher()
        MatcherAssert.assertThat(
            mainViewModel.showLoading.getOrAwaitValue(),
            CoreMatchers.equalTo(false)
        )
    }

    @Test
    fun showError_whenLoadingError() = mainCoroutineRule.runBlockingTest {
        dataSource.errorMsg = "Failed"
        mainViewModel.getTopRatedMovies()
        MatcherAssert.assertThat(
            mainViewModel.showSnackBar.getOrAwaitValue(), CoreMatchers.equalTo("Failed")
        )
    }
    //Testing LiveData
}