package com.example.moviesapp.ui

import android.app.Application
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.moviesapp.DataSource
import com.example.moviesapp.data.FakeDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import com.example.moviesapp.R


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class MainFragmentTest {

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var context: Application


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setup() {
        //stop original koin to inject fake repository
//        stopKoin()
        context = ApplicationProvider.getApplicationContext()
        fakeDataSource = FakeDataSource()

        val myModule = module {
            viewModel {
                MainViewModel(fakeDataSource, context)
            }

            single { fakeDataSource as DataSource }

        }
        startKoin {
            modules(listOf(myModule))

        }

    }


    @Test
    fun showAllMoviesList() {
        launchFragmentInContainer<MainFragment>(Bundle(), R.style.AppTheme)
        Thread.sleep(6000)
            Espresso.onView(withText("title1"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withText("overview1"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        }



}
