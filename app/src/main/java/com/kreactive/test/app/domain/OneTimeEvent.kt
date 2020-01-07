package com.kreactive.test.app.domain

open class OneTimeEvent<T>(private val data: T) {

    private var doneBeenGot = false

    open fun get(): T? {
        if (doneBeenGot) return null
        doneBeenGot = true
        return data
    }

    fun peek(): T {
        return data
    }
}

class BooleanOneTimeEvent(value: Boolean = true) : OneTimeEvent<Boolean>(value) {
    override fun get(): Boolean {
        return super.get() ?: false
    }
}
