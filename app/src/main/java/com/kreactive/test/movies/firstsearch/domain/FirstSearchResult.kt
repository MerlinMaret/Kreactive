package com.kreactive.test.movies.firstsearch.domain

import com.kreactive.test.app.domain.Result

sealed class FirstSearchResult : Result {
    class SearchChange(val search: String) : FirstSearchResult()
    class GoToList(val search: String) : FirstSearchResult()
    class EmptySearch : FirstSearchResult()
}
