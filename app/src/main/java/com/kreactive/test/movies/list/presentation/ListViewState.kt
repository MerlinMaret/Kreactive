package com.kreactive.test.movies.list.presentation


import com.kreactive.test.app.presentation.ViewState

//TODO : Add ViewModel data
data class ListViewState(
    val search : String = ""
) : ViewState
