package com.kreactive.test.movies.list.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.repository.MovieRepository
import com.kreactive.test.app.domain.BaseController
import com.kreactive.test.movies.list.model.MovieListItem

object ListController : BaseController<ListResult, ListAction>() {

    var movieLiveData: LiveData<List<Movie>>? = null
    var moviesObserver: Observer<List<Movie>>? = null
    var searchObserver: Observer<Search?>? = null

    //region processImpl

    override fun processImpl(action: ListAction) {
        when (action) {
            is ListAction.FirstInit -> firstInit(action)
            is ListAction.SearchChange -> searchChange(action)
            is ListAction.ClickSearch -> clickSearch(action)
            is ListAction.AddOnList -> addOnList(action)
        }
    }

    private fun firstInit(action: ListAction.FirstInit) {
        results.value = ListResult.SetSearch(action.search)
        search(action.search, false)
    }

    private fun searchChange(action: ListAction.SearchChange) {
        results.value = ListResult.SetSearch(action.search)
    }

    private fun clickSearch(action: ListAction.ClickSearch) {
        search(action.search, true)
    }

    private fun addOnList(action: ListAction.AddOnList) {
        search(action.search, false)
    }

    //endregion

    private fun search(search: String, reset : Boolean) {
        val searchLiveData = MovieRepository.loadSearch(search)

        searchObserver = Observer {
            val page = if(!reset) it?.maxPageLoaded ?: 0 else 0
            val nextPage = page + 1
            moviesObserver?.let { movieLiveData?.removeObserver(it) }
            movieLiveData = MovieRepository.loadMovies(search, nextPage)
            moviesObserver = Observer { movies ->
                results.value = ListResult.UpdateList(
                    movies.map {
                        MovieListItem.fromMovie(it)
                    }
                )
            }
            moviesObserver?.let { movieLiveData?.observeForever(it) }
            searchObserver?.let { searchLiveData.removeObserver(it) }
        }
        searchObserver?.let { searchLiveData.observeForever(it) }
    }
}
