package com.example.moviesapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.moviesapp.data.database.MoviesDataBase
import com.example.moviesapp.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class MoviesDataBaseDaoTest {

    private lateinit var database: MoviesDataBase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            MoviesDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun insertMovieAndGet() = runBlockingTest {
        val insertedMovie = Movie(
            1, "title", "poster_path", "overview"
        )
        database.moviesDatabaseDao.insertAll(insertedMovie)

        val loadedMovie = database.moviesDatabaseDao.getAllMovies()

        loadedMovie.collect {
            val movie = it.get(0)
            MatcherAssert.assertThat(movie, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(movie.id, `is`(insertedMovie.id))
            MatcherAssert.assertThat(movie.title, `is`(insertedMovie.title))
            MatcherAssert.assertThat(movie.poster_path, `is`(insertedMovie.poster_path))
            MatcherAssert.assertThat(movie.overview, `is`(insertedMovie.overview))


        }
    }


}