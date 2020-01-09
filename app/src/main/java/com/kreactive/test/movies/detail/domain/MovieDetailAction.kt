package com.kreactive.test.movies.detail.domain

import com.kreactive.test.app.domain.Action

sealed class MovieDetailAction : Action {
    class InitView(val movieId: String) : MovieDetailAction()
}
