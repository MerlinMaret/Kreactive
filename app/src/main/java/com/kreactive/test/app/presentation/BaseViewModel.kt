package com.kreactive.test.app.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.kreactive.test.app.domain.Action
import com.kreactive.test.app.domain.BaseController
import com.kreactive.test.app.domain.Result

abstract class BaseViewModel<A : Action, VS : ViewState, R : Result>(private val controller: BaseController<R, A>) :
    ViewModel() {

    val viewState: MutableLiveData<VS> = MutableLiveData()

    private val actions: MutableLiveData<A> = MutableLiveData()
    private val actionsObserver: Observer<A> = Observer {
        controller.processImpl(it)
    }

    private val resultsObserver: Observer<R> = Observer {
        onResultReceived(it)
    }

    protected abstract val initialValue: VS

    protected fun initControllerSubscriptions() {
        actions.observeForever(actionsObserver)
        controller.results.observeForever(resultsObserver)
        viewState.value = initialValue
    }

    private fun onResultReceived(result: R) {
        if (resultFilter(result)) {
            viewState.value = reducer(viewState.value, result)
        }
    }

    protected abstract fun reducer(viewState: VS?, result: R): VS

    fun action(action: A) {
        actions.value = action
    }

    protected open fun resultFilter(result: R): Boolean {
        return true
    }

    override fun onCleared() {
        actions.removeObserver(actionsObserver)
        controller.results.removeObserver(resultsObserver)
        super.onCleared()
    }
}
