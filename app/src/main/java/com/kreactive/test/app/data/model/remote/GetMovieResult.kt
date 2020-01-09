package com.kreactive.test.app.data.model.remote

import com.google.gson.annotations.SerializedName
import com.kreactive.test.app.data.model.local.Movie
import java.util.*

data class GetMovieResult(

    @SerializedName("Title")
    val title : String,

    @SerializedName("Actors")
    val actors : String,

    @SerializedName("Year")
    val year : String,

    @SerializedName("Poster")
    val poster : String
) {
    fun toMovie(movieId : String): Movie {
        return Movie(
            movieId,
            title,
            poster,
            year,
            actors,
            createdDate = Calendar.getInstance().timeInMillis
        )
    }
}