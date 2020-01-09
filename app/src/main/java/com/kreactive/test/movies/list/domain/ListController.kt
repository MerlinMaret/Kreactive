package com.kreactive.test.movies.list.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.repository.MovieRepository
import com.kreactive.test.app.domain.BaseController
import com.kreactive.test.app.domain.DataState
import com.kreactive.test.movies.list.model.MovieListItem
import kotlinx.coroutines.*

object ListController : BaseController<ListResult, ListAction>() {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    //region processImpl

    override fun processImpl(action: ListAction) {
        when (action) {
            is ListAction.FirstInit -> firstInit(action)
            is ListAction.SearchChange -> searchChange(action)
            is ListAction.ClickSearch -> clickSearch(action)
            is ListAction.AddOnList -> addOnList(action)
            is ListAction.Refresh -> refresh(action)
            is ListAction.ClickItem -> clickItem(action)
        }
    }

    private fun firstInit(action: ListAction.FirstInit) {
        results.value = ListResult.SetSearch(action.search)
        moviesObserver?.let { movieLiveData?.removeObserver(it) }
        movieLiveData = MovieRepository.loadFirstPage(action.search)
        moviesObserver?.let { movieLiveData?.observeForever(it) }
    }

    private fun searchChange(action: ListAction.SearchChange) {
        results.value = ListResult.SetSearch(action.search)
    }

    private fun clickSearch(action: ListAction.ClickSearch) {
        moviesObserver?.let { movieLiveData?.removeObserver(it) }
        movieLiveData = MovieRepository.loadFirstPage(action.search)
        moviesObserver?.let { movieLiveData?.observeForever(it) }
    }

    private fun addOnList(action: ListAction.AddOnList) {
        moviesObserver?.let { movieLiveData?.removeObserver(it) }
        movieLiveData = MovieRepository.loadMoreMovies(action.search)
        moviesObserver?.let { movieLiveData?.observeForever(it) }
    }

    private fun refresh(action: ListAction.Refresh) {
        moviesObserver?.let { movieLiveData?.removeObserver(it) }
        movieLiveData = MovieRepository.resetSearch(action.search)
        moviesObserver?.let { movieLiveData?.observeForever(it) }
    }

    private fun clickItem(action: ListAction.ClickItem) {
        results.value = ListResult.GoToDetail(action.id)
    }

    //endregion

    var movieLiveData: LiveData<DataState<List<Movie>>>? = null
    val moviesObserver: Observer<DataState<List<Movie>>>? = Observer { dataState ->

        val movies = dataState.data?.sortedWith(
            compareBy({ it.createdDate }, { it.title })
        )?.map {
            MovieListItem.fromMovie(it)
        }
        results.value = ListResult.UpdateList(
            movies,
            dataState.status
        )
    }
}
