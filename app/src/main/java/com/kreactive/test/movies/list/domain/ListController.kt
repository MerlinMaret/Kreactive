package com.kreactive.test.movies.list.domain

import com.kreactive.test.app.domain.BaseController

object ListController : BaseController<ListResult, ListAction>() {

    override fun processImpl(action: ListAction) {
        when (action) {
            is ListAction.ViewInit -> viewInit(action)
        }
    }

    private fun viewInit(action: ListAction.ViewInit) {
        results.value = ListResult.SetSearch(action.search)
    }
}
