package com.kreactive.test.movies.detail.domain

import com.kreactive.test.app.domain.Result

sealed class MovieDetailResult : Result {
    class UpdateMovie(val title: String, val actors: String?, val posterUrl: String) : MovieDetailResult()
}
