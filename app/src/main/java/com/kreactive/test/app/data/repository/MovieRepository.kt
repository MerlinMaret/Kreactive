package com.kreactive.test.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kreactive.test.app.data.datasource.LocalDataSource
import com.kreactive.test.app.data.datasource.RemoteDatasource
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import kotlinx.coroutines.*

object MovieRepository {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    val loadingState: MutableLiveData<Boolean> = MutableLiveData()

    //region List

    fun getSearch(search: String): LiveData<Search> {
        return LocalDataSource.getSearch(search)
    }

    suspend fun getSearchSync(search: String): Search? = withContext(Dispatchers.IO) {
        LocalDataSource.getSearchAsync(search)
    }

    fun loadMovies(search: String, page: Int?, reset: Boolean): LiveData<List<Movie>> {
        if(page != null){
            loadMoviesFromRemote(search, page, reset)
        }
        return LocalDataSource.getMovies(search)
    }

    fun loadMoviesFromRemote(search: String, page: Int, reset: Boolean) {
        coroutineScope.launch {
            if (reset) {
                LocalDataSource.removeSearch(search)
            }

            loadingState.value = true
            RemoteDatasource.searchMovie(
                search,
                page,
                {
                    this.launch {
                        loadingState.value = false
                    }
                },
                {

                    coroutineScope.launch {
                        it?.body()?.let { results ->
                            val movies = results.search.map { it.toMovie() }
                            LocalDataSource.setMovies(
                                results.toSearch(search, page),
                                movies
                            )
                            loadingState.value = false
                        }
                    }
                }
            )
        }
    }

    //endregion

    //region Detail

    fun loadMovie(movieId: String): LiveData<Movie> {
        loadMovieFromRemote(movieId)
        return LocalDataSource.getMovie(movieId)
    }

    fun loadMovieFromRemote(movieId: String) {
        coroutineScope.launch {
            RemoteDatasource.getMovie(
                movieId,
                {
                    //Nothing
                },
                {
                    coroutineScope.launch {
                        val movie = LocalDataSource.loadMovieAsync(movieId).copy(
                            actors = it?.body()?.actors
                        )
                        movie.let { LocalDataSource.setMovie(it) }
                    }
                }
            )
        }
    }

    //endregion
}