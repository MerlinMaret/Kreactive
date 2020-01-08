package com.kreactive.test.app.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey


const val MOVIE_TABLE_NAME = "Movie"

@Entity(tableName = MOVIE_TABLE_NAME)
data class Movie(
    @PrimaryKey
    val id : String,
    val title : String
)