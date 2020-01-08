package com.kreactive.test.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kreactive.test.app.data.model.local.MOVIE_TABLE_NAME
import com.kreactive.test.app.data.model.local.Movie

@Dao
interface MovieDao{

    @Query("Select * FROM $MOVIE_TABLE_NAME")
    fun getAll(): LiveData<List<Movie>>

    @Query("Select * FROM $MOVIE_TABLE_NAME WHERE title = :title")
    fun search(title : String): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)

}