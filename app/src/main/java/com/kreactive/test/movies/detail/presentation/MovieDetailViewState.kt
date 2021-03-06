package com.kreactive.test.movies.detail.presentation


import com.kreactive.test.app.domain.DataState
import com.kreactive.test.app.presentation.ViewState

//TODO : Add ViewModel data
data class MovieDetailViewState(
    val title : String? = null,
    val posterUrl : String? = null,
    val actors : String? = null,
    val networkStatus: DataState.NetworkStatus = DataState.NetworkStatus.EMPTY
) : ViewState
