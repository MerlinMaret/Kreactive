package com.kreactive.test.app.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.model.local.SearchMovieJoin

@Database(entities = arrayOf(Movie::class, Search::class, SearchMovieJoin::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
    abstract fun searchDao() : SearchDao
    abstract fun searchmoviejoinDao() : SearchMovieJoinDao
}