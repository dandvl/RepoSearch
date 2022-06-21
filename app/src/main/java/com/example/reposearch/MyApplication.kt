package com.example.reposearch

import android.app.Application
import com.example.reposearch.api.Repository
import com.example.reposearch.api.WebService
import com.example.reposearch.ui.RepoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(moduleOne)
        }
    }

    private val moduleOne = module {
        single { WebService }
        single { Repository(get()) }
        viewModel { RepoViewModel(get()) }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}