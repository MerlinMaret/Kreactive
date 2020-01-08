package com.kreactive.test.movies.list.domain

import com.kreactive.test.app.domain.Action

sealed class ListAction : Action {
    class FirstInit(val search: String) : ListAction()
    class SearchChange(val search: String) : ListAction()
    class ClickSearch(val search: String) : ListAction()
    class AddOnList(val search: String) : ListAction()
}
