package com.kreactive.test.movies.list.domain

import com.kreactive.test.app.domain.Action

sealed class ListAction : Action {
    class ViewInit(val search: String) : ListAction()
}
