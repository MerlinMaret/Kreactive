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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object LocalDataSource {

    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var searchDao: SearchDao
    private lateinit var searchMovieJoinDao: SearchMovieJoinDao

    fun initDatabase(context: Context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "test.db").build()
        movieDao = appDatabase.movieDao()
        searchDao = appDatabase.searchDao()
        searchMovieJoinDao = appDatabase.searchmoviejoinDao()
    }

    fun getSearch(search: String): LiveData<Search> {
        return searchDao.get(search)
    }

    suspend fun getSearchAsync(search: String): Search? = withContext(Dispatchers.IO) {
        searchDao.getSync(search)
    }


    fun getMovies(search: String): LiveData<List<Movie>> {
        return searchMovieJoinDao.getMoviesForSearch(search)
    }


    fun getMovie(movieId: String): LiveData<Movie> {
        return movieDao.get(movieId)
    }


    suspend fun loadMovieAsync(movieId: String): Movie = withContext(Dispatchers.IO) {
        movieDao.getSync(movieId)
    }


    suspend fun setMovies(search: Search, movies: List<Movie>) = withContext(Dispatchers.IO) {
        movieDao.add(movies)
        searchDao.add(search)
        for (movie in movies) {
            searchMovieJoinDao.add(
                SearchMovieJoin(
                    search.search,
                    movie.id
                )
            )
        }
    }

    suspend fun removeSearch(search: String) = withContext(Dispatchers.IO) {
        searchDao.delete(search)
        searchMovieJoinDao.delete(search)
    }

    suspend fun setMovie(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.add(movie)
    }
}