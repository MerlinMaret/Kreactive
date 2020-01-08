package com.kreactive.test.movies.list.presentation


import com.kreactive.test.app.domain.BooleanOneTimeEvent
import com.kreactive.test.app.presentation.ViewState
import com.kreactive.test.movies.list.model.MovieListItem

//TODO : Add ViewModel data
data class ListViewState(
    val search : String = "",
    val movies : List<MovieListItem> = emptyList(),
    val firstLoad : BooleanOneTimeEvent = BooleanOneTimeEvent()
) : ViewState
