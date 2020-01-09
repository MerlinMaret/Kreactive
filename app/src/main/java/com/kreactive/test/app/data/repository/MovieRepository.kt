package com.kreactive.test.app.data.repository

import androidx.lifecycle.LiveData
import com.kreactive.test.app.data.datasource.LocalDataSource
import com.kreactive.test.app.data.datasource.RemoteDatasource
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.model.remote.SearchMoviesResult
import com.kreactive.test.app.domain.DataState
import kotlinx.coroutines.*

object MovieRepository : BaseRepository() {

    suspend fun getSearchSync(search: String): Search? = withContext(Dispatchers.IO) {
        LocalDataSource.getSearchAsync(search)
    }

    fun loadFirstPage(search: String): LiveData<DataState<List<Movie>>>{
        val firstPage = 1
        return loadData(
            {
                LocalDataSource.getMovies(search)
            },
            {
                val page = getSearchSync(search)?.maxPageLoaded
                if(page == null){
                    RemoteDatasource.searchMovie(search,firstPage)
                }
                else {
                    null
                }
            },
            {
                onSearchMoviesResult(it, search, firstPage)
            }
        )
    }

    fun resetSearch(search: String): LiveData<DataState<List<Movie>>>{
        val firstPage = 1
        return loadData(
            {
                LocalDataSource.getMovies(search)
            },
            {
                LocalDataSource.removeSearch(search)
                RemoteDatasource.searchMovie(search,firstPage)
            },
            {
                onSearchMoviesResult(it, search, firstPage)
            }
        )
    }

    fun loadMoreMovies(search: String): LiveData<DataState<List<Movie>>> {
        var page = 1
        return loadData(
            {
                LocalDataSource.getMovies(search)
            },
            {
                page = (getSearchSync(search)?.maxPageLoaded ?: page) + 1
                RemoteDatasource.searchMovie(search,page)
            },
            {
                onSearchMoviesResult(it, search, page)
            }
        )
    }

    private suspend fun onSearchMoviesResult(searchMoviesResult: SearchMoviesResult, search : String, page : Int) = withContext(Dispatchers.IO){
        val movies = searchMoviesResult.search?.map { it.toMovie() }
        if(movies != null){
            LocalDataSource.setSearchMovies(
                searchMoviesResult.toSearch(search, page),
                movies
            )
        }
    }


    fun loadMovie(movieId: String): LiveData<DataState<Movie>> {

        return loadData(
            { LocalDataSource.getMovie(movieId) },
            { RemoteDatasource.getMovie(movieId) },
            {
                val movie = it.toMovie(movieId)
                LocalDataSource.updateMovie(movie)
            }
        )
    }

}