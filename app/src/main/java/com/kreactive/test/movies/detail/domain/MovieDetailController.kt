package com.kreactive.test.movies.detail.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.repository.MovieRepository
import com.kreactive.test.app.domain.BaseController
import com.kreactive.test.app.domain.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object MovieDetailController : BaseController<MovieDetailResult, MovieDetailAction>() {


    override fun processImpl(action: MovieDetailAction) {
        when (action) {
            is MovieDetailAction.InitView -> initView(action)
        }
    }

    var movieLiveData: LiveData<DataState<Movie>>? = null
    val movieObserver: Observer<DataState<Movie>>? = Observer { dataState ->

        val movie = dataState.data

        results.value = MovieDetailResult.UpdateMovie(
            movie?.title,
            movie?.actors,
            movie?.posterUrl,
            dataState.status
        )
    }

    fun initView(action: MovieDetailAction.InitView) {
        movieObserver?.let { movieLiveData?.removeObserver(it) }
        movieLiveData = MovieRepository.loadMovie(action.movieId)
        movieObserver?.let { movieLiveData?.observeForever(it) }
    }
}
