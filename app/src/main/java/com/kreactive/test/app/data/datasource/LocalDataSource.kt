package com.kreactive.test.app.data.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.kreactive.test.app.data.dao.AppDatabase
import com.kreactive.test.app.data.dao.MovieDao
import com.kreactive.test.app.data.dao.SearchDao
import com.kreactive.test.app.data.dao.SearchMovieJoinDao
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.model.local.SearchMovieJoin

object LocalDataSource {

    private lateinit var appDatabase : AppDatabase
    private lateinit var movieDao : MovieDao
    private lateinit var searchDao : SearchDao
    private lateinit var searchMovieJoinDao: SearchMovieJoinDao

    fun initDatabase(context : Context){
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "test.db").build()
        movieDao = appDatabase.movieDao()
        searchDao = appDatabase.searchDao()
        searchMovieJoinDao = appDatabase.searchmoviejoinDao()
    }

    fun loadSearch(search : String) : LiveData<Search>{
        return searchDao.get(search)
    }


    fun searchMovie(search : String) : LiveData<List<Movie>>{
        return searchMovieJoinDao.getMoviesForSearch(search)
    }

    fun saveMovies(search : Search, movies: List<Movie>) {
        Thread {
            movieDao.add(movies)
            searchDao.add(search)
            for (movie in movies){
                searchMovieJoinDao.add(
                    SearchMovieJoin(
                        search.search,
                        movie.id
                    )
                )
            }
        }.start()
    }
}