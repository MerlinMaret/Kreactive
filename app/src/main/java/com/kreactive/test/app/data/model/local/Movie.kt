package com.kreactive.test.app.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


const val MOVIE_TABLE_NAME = "Movie"

@Entity(tableName = MOVIE_TABLE_NAME)
data class Movie(
    @PrimaryKey
    val id : String,
    val title : String,
    val posterUrl : String,
    val year : String,
    val actors : String?,
    val createdDate : Long = Calendar.getInstance().timeInMillis
)