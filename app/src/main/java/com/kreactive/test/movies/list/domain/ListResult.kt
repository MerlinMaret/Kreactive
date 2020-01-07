package com.kreactive.test.movies.list.domain

import com.kreactive.test.app.domain.Result

sealed class ListResult : Result {
    class SetSearch(val search: String) : ListResult()
}
