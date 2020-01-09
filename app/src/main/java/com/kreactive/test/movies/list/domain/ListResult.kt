package com.kreactive.test.movies.list.domain

import com.kreactive.test.app.domain.Result
import com.kreactive.test.movies.list.model.MovieListItem

sealed class ListResult : Result {
    class SetSearch(val search: String) : ListResult()
    class UpdateList(val list : List<MovieListItem>) : ListResult()
    class IsRefreshing(val isRefreshing: Boolean) : ListResult()
    class GoToDetail(val id: String?) : ListResult()
}
