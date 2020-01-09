package com.kreactive.test.movies.list.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.kreactive.test.app.data.repository.MovieRepository
import com.kreactive.test.app.domain.BaseController
import com.kreactive.test.movies.list.model.MovieListItem
import kotlinx.coroutines.*

object ListController : BaseController<ListResult, ListAction>() {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    var movieLiveData: LiveData<List<Movie>>? = null
    var moviesObserver: Observer<List<Movie>>? = null
    //region processImpl

    init {
        MovieRepository.loadingState.observeForever {
            results.value = ListResult.IsRefreshing(it)
        }
    }

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
        search(action.search, false, true)
    }

    private fun searchChange(action: ListAction.SearchChange) {
        results.value = ListResult.SetSearch(action.search)
    }

    private fun clickSearch(action: ListAction.ClickSearch) {
        search(action.search, true, false)
    }

    private fun addOnList(action: ListAction.AddOnList) {
        search(action.search, false, false)
    }

    private fun refresh(action: ListAction.Refresh) {
        search(action.search, true, false)
    }

    private fun clickItem(action: ListAction.ClickItem) {
        results.value = ListResult.GoToDetail(action.id)
    }

    //endregion

    private fun search(search: String, reset: Boolean, isFirstCall : Boolean) {

        coroutineScope.launch {

            moviesObserver?.let { movieLiveData?.removeObserver(it) }

            val nextPage = getPageToLoad(search, reset, isFirstCall)

            movieLiveData = MovieRepository.loadMovies(search, nextPage, reset)

            moviesObserver = Observer { movies ->
                results.value = ListResult.UpdateList(
                    movies.sortedWith(compareBy({ it.createdDate }, { it.title }))
                        .map { MovieListItem.fromMovie(it) }
                )
            }

            moviesObserver?.let { movieLiveData?.observeForever(it) }
        }
    }

    suspend fun getPageToLoad(search: String, reset: Boolean, isFirstCall: Boolean) : Int? = withContext(Dispatchers.IO){
        val searchItem = MovieRepository.getSearchSync(search)
        val nextPage : Int?
        if(searchItem != null && isFirstCall){
            nextPage = null
        }
        else {
            val page = if (!reset) searchItem?.maxPageLoaded ?: 0 else 0
            nextPage = page + 1
        }
        nextPage
    }
}
