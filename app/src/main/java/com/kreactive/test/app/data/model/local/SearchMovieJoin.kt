package com.kreactive.test.app.data.model.local

import androidx.room.Entity
import androidx.room.ForeignKey


const val SEARCH_MOVIE_JOIN_TABLE_NAME = "SearchMovieJoin"

@Entity(tableName = SEARCH_MOVIE_JOIN_TABLE_NAME, primaryKeys = arrayOf("search","movieId"))
data class SearchMovieJoin (

    @ForeignKey(entity = Search::class, parentColumns = arrayOf("search"), childColumns = arrayOf("search"))
    val search : String,
    @ForeignKey(entity = Movie::class, parentColumns = arrayOf("movieId"), childColumns = arrayOf("id"))
    val movieId : String
)