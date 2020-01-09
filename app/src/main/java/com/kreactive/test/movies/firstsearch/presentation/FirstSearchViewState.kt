package com.kreactive.test.movies.firstsearch.presentation


import com.kreactive.test.app.domain.BooleanOneTimeEvent
import com.kreactive.test.app.presentation.ViewState

//TODO : Add ViewModel data
data class FirstSearchViewState(
    val search : String = "",
    val emptySearchError : BooleanOneTimeEvent = BooleanOneTimeEvent(false),
    val goToList : BooleanOneTimeEvent = BooleanOneTimeEvent(false)
) : ViewState
