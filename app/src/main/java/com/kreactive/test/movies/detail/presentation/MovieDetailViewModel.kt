package com.kreactive.test.movies.detail.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kreactive.test.app.presentation.BaseViewModel
import com.kreactive.test.movies.detail.domain.MovieDetailAction
import com.kreactive.test.movies.detail.domain.MovieDetailController
import com.kreactive.test.movies.detail.domain.MovieDetailResult

class MovieDetailViewModel(controller: MovieDetailController) :
    BaseViewModel<MovieDetailAction, MovieDetailViewState, MovieDetailResult>(controller) {

    override val initialValue: MovieDetailViewState = MovieDetailViewState()

    companion object {
        fun getFragmentInstance(fragment: Fragment): MovieDetailViewModel {
            val factory = Factory(MovieDetailController)
            return ViewModelProvider(fragment, factory).get(MovieDetailViewModel::class.java)
        }
    }

    init {
        initControllerSubscriptions()
    }

    //region Reducer

    override fun reducer(
        currentState: MovieDetailViewState?,
        result: MovieDetailResult
    ): MovieDetailViewState {
        var viewState = currentState ?: MovieDetailViewState()
        return when (result) {
            is MovieDetailResult.UpdateMovie -> viewState.copy(
                result.title,
                result.posterUrl,
                result.actors
            )
        }
    }

    //endregion

    class Factory(private val controller: MovieDetailController) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(controller) as T
        }
    }
}
