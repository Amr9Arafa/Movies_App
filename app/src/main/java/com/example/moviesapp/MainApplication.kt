package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.database.MoviesDataBase
import com.example.moviesapp.repository.MoviesRepository
import com.example.moviesapp.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.reflect.jvm.internal.impl.metadata.jvm.JvmModuleProtoBuf.Module

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val myModule = module {

            viewModel {
                MainViewModel(
                    get() as DataSource,
                    get()
                )
            }

            single { MoviesRepository(get()) as DataSource }
            single { MoviesDataBase.getInstance(this@MainApplication) }
        }

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(myModule))
        }
    }
}