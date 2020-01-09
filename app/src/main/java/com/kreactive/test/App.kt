package com.kreactive.test

import android.app.Application
import com.kreactive.test.app.data.datasource.LocalDataSource
import com.kreactive.test.app.data.repository.MovieRepository
import com.kreactive.test.movies.detail.domain.MovieDetailController
import com.kreactive.test.movies.list.domain.ListController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

//Template version : 1.0.0

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalDataSource.initDatabase(applicationContext)
    }
}
