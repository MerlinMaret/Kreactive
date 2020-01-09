package com.kreactive.test.app.domain

import androidx.lifecycle.MutableLiveData

abstract class BaseController<R : Result, A : Action> {

    val results: MutableLiveData<R> = MutableLiveData()

    abstract fun processImpl(action: A)
}
