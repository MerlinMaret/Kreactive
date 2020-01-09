package com.kreactive.test.movies.list.model

import com.kreactive.test.app.data.model.local.Movie

data class MovieListItem(
    val id : String,
    val title : String,
    val posterUrl : String,
    val year : String
) {

    companion object {
        fun fromMovie(movie : Movie) : MovieListItem{
            return MovieListItem(
                movie.id,
                movie.title,
                movie.posterUrl,
                movie.year
            )
        }
    }
}