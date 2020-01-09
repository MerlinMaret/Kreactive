package com.kreactive.test.movies.firstsearch.domain

import com.kreactive.test.app.domain.Action

sealed class FirstSearchAction : Action {
    class SearchChange(val search : String) : FirstSearchAction()
    class ClickSearch(val search: String?) : FirstSearchAction()
}
