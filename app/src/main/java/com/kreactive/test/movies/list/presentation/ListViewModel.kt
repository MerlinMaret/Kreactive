package com.kreactive.test.movies.list.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kreactive.test.app.domain.OneTimeEvent
import com.kreactive.test.app.presentation.BaseViewModel
import com.kreactive.test.movies.list.domain.ListAction
import com.kreactive.test.movies.list.domain.ListController
import com.kreactive.test.movies.list.domain.ListResult

class ListViewModel(controller: ListController) :
    BaseViewModel<ListAction, ListViewState, ListResult>(controller) {

    override val initialValue: ListViewState = ListViewState()

    companion object {
        fun getFragmentInstance(fragment: Fragment): ListViewModel {
            val factory = Factory(ListController)
            return ViewModelProvider(fragment, factory).get(ListViewModel::class.java)
        }
    }

    init {
        initControllerSubscriptions()
    }

    fun addOnList(){
        val search = viewState.value?.search ?: ""
        val addOnList = ListAction.AddOnList(search)
        action(addOnList)
    }

    //region Reducer

    override fun reducer(currentState: ListViewState?, result: ListResult): ListViewState {
        var viewState = currentState ?: ListViewState()
        return when (result) {
            is ListResult.SetSearch -> viewState.copy(
                result.search
            )
            is ListResult.UpdateList -> viewState.copy(
                movies = result.list ?: viewState.movies,
                networkState = OneTimeEvent(result.networkStatus)
            )
            is ListResult.GoToDetail -> viewState.copy(
                goToDetail = OneTimeEvent(result.id)
            )
        }
    }

    //endregion

    class Factory(private val controller: ListController) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewModel(controller) as T
        }
    }
}
