package com.kreactive.test.movies.list.presentation


import com.kreactive.test.app.domain.BooleanOneTimeEvent
import com.kreactive.test.app.presentation.ViewState
import com.kreactive.test.movies.list.model.Movie

//TODO : Add ViewModel data
data class ListViewState(
    val search : String = "",
    val movies : List<Movie> = emptyList(),
    val firstLoad : BooleanOneTimeEvent = BooleanOneTimeEvent()
) : ViewState
