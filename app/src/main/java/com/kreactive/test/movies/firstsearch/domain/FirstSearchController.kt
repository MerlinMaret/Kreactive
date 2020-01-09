package com.kreactive.test.movies.firstsearch.domain

import android.text.TextUtils
import com.kreactive.test.app.domain.BaseController

object FirstSearchController : BaseController<FirstSearchResult, FirstSearchAction>() {

    override fun processImpl(action: FirstSearchAction) {
        when (action) {
            is FirstSearchAction.SearchChange -> searchChange(action)
            is FirstSearchAction.ClickSearch -> clickSearch(action)
        }
    }

    private fun searchChange(action: FirstSearchAction.SearchChange) {
        results.value = FirstSearchResult.SearchChange(action.search)
    }

    private fun clickSearch(action: FirstSearchAction.ClickSearch){
        if(TextUtils.isEmpty(action.search)){
            results.value = FirstSearchResult.EmptySearch()
        }
        else {
            results.value = FirstSearchResult.GoToList(action.search!!)
        }
    }
}
