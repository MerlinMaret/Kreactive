package com.kreactive.test.movies.firstsearch.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kreactive.test.app.domain.BooleanOneTimeEvent
import com.kreactive.test.app.presentation.BaseViewModel
import com.kreactive.test.movies.firstsearch.domain.FirstSearchAction
import com.kreactive.test.movies.firstsearch.domain.FirstSearchController
import com.kreactive.test.movies.firstsearch.domain.FirstSearchResult

class FirstSearchViewModel(controller: FirstSearchController) :
    BaseViewModel<FirstSearchAction, FirstSearchViewState, FirstSearchResult>(controller) {

    override val initialValue: FirstSearchViewState = FirstSearchViewState()

    companion object {
        fun getFragmentInstance(fragment: Fragment): FirstSearchViewModel {
            val factory = Factory(FirstSearchController)
            return ViewModelProvider(fragment, factory).get(FirstSearchViewModel::class.java)
        }
    }

    init {
        initControllerSubscriptions()
    }

    //region Reducer

    override fun reducer(
        currentState: FirstSearchViewState?,
        result: FirstSearchResult
    ): FirstSearchViewState {
        var viewState = currentState ?: FirstSearchViewState()
        return when (result) {
            is FirstSearchResult.SearchChange -> viewState.copy(
                search = result.search
            )
            is FirstSearchResult.GoToList -> viewState.copy(
                goToList = BooleanOneTimeEvent()
            )
            is FirstSearchResult.EmptySearch -> viewState.copy(
                emptySearchError = BooleanOneTimeEvent()
            )

        }
    }

    //endregion

    class Factory(private val controller: FirstSearchController) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FirstSearchViewModel(controller) as T
        }
    }
}
