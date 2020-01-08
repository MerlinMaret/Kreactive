package com.kreactive.test.movies.list.domain

import com.kreactive.test.app.domain.BaseController

object ListController : BaseController<ListResult, ListAction>() {

   //region processImpl

    override fun processImpl(action: ListAction) {
        when (action) {
            is ListAction.FirstInit -> firstInit(action)
            is ListAction.SearchChange -> searchChange(action)
            is ListAction.ClickSearch -> clickSearch(action)
        }
    }

    private fun firstInit(action: ListAction.FirstInit) {
        results.value = ListResult.SetSearch(action.search)
    }

    private fun searchChange(action: ListAction.SearchChange) {
        results.value = ListResult.SetSearch(action.search)
    }

    private fun clickSearch(action: ListAction.ClickSearch) {
        //TODO
    }

    //endregion
}
