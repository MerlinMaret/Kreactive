package com.kreactive.test.app.data.model.remote

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.annotations.SerializedName
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.Search
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


data class SearchMoviesResult(
    @SerializedName("Search")
    val search: List<SearchMovieResult>? = null,

    @SerializedName("totalResults")
    val totalResults: Int? = null,

    @SerializedName("Response")
    val response: Boolean? = null,

    @SerializedName("Error")
    val error: String? = null

) {
    fun toSearch(search: String, pageNumber: Int): Search {
        return Search(
            search,
            totalResults ?: 0,
            pageNumber
        )
    }
}

class SearchDeserializer : JsonDeserializer<SearchMoviesResult> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): SearchMoviesResult {
        val jsonObject = json.asJsonObject
        val gson = Gson()

        val searchMovieResult = gson.fromJson(jsonObject, SearchMoviesResult::class.java)

        if(searchMovieResult.error != null){
            throw Throwable("TODO")
        }

        return searchMovieResult
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

    fun toMovie(): Movie {
        return Movie(
            imdbID,
            title,
            poster,
            year,
            null
        )
    }
}