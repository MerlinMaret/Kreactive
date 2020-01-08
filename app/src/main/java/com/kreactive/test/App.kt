package com.kreactive.test

import android.app.Application
import com.kreactive.test.app.data.datasource.LocalDataSource

//Template version : 1.0.0

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalDataSource.initDatabase(applicationContext)
    }
}
