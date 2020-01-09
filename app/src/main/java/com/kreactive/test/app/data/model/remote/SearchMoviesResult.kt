package com.kreactive.test.app.data.model.remote

import com.google.gson.annotations.SerializedName
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search

data class SearchMoviesResult(
    @SerializedName("Search")
    val search: List<SearchMovieResult>,

    @SerializedName("totalResults")
    val totalResults: Int,

    @SerializedName("Response")
    val response: Boolean
) {
    fun toSearch(search : String, pageNumber : Int) : Search {
        return Search(
            search,
            totalResults,
            pageNumber
        )
    }
}

data class SearchMovieResult(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String
) {

    fun toMovie() : Movie {
        return Movie(
            imdbID,
            title,
            poster,
            year,
            null
        )
    }
}