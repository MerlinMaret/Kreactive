package com.kreactive.test.movies.detail.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.repository.MovieRepository
import com.kreactive.test.app.domain.BaseController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object MovieDetailController : BaseController<MovieDetailResult, MovieDetailAction>() {

    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    var movieLiveData: LiveData<Movie>? = null
    var movieObserver: Observer<Movie>? = null

    override fun processImpl(action: MovieDetailAction) {
        when (action) {
            is MovieDetailAction.InitView -> initView(action)
        }
    }

    fun initView(action: MovieDetailAction.InitView) {

        coroutineScope.launch {
            movieObserver?.let { movieLiveData?.removeObserver(it) }
            movieLiveData = MovieRepository.loadMovie(action.movieId)

            movieObserver = Observer { movie ->
                results.value = MovieDetailResult.UpdateMovie(
                    movie.title,
                    movie.actors,
                    movie.posterUrl
                )
            }
            movieObserver?.let { movieLiveData?.observeForever(it) }
        }
    }
}
