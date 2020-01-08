package com.kreactive.test.app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kreactive.test.app.data.datasource.LocalDataSource
import com.kreactive.test.app.data.datasource.RemoteDatasource
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.model.remote.SearchMoviesResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepository {

    fun loadSearch(search: String): LiveData<Search> {
        return LocalDataSource.loadSearch(search)
    }

    fun loadMovies(search: String, page : Int): LiveData<List<Movie>> {
        loadMoviesFromRemote(search, page)
        return LocalDataSource.searchMovie(search)
    }

    fun loadMoviesFromRemote(search: String, page : Int) {

        //TODO add dynamique page
        RemoteDatasource.omdbapi.searchMovie(search, page).enqueue(
            object : Callback<SearchMoviesResult> {
                override fun onFailure(call: Call<SearchMoviesResult>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<SearchMoviesResult>?,
                    response: Response<SearchMoviesResult>?
                ) {
                    response?.body()?.let { results ->
                        val movies = results.search.map { it.toMovie() }
                        LocalDataSource.saveMovies(
                            results.toSearch(search,page),
                            movies)
                    }
                }
            }
        )
    }
}