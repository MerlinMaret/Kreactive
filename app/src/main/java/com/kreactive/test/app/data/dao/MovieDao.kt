package com.kreactive.test.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kreactive.test.app.data.model.local.MOVIE_TABLE_NAME
import com.kreactive.test.app.data.model.local.Movie

@Dao
interface MovieDao{

    @Query("Select * FROM $MOVIE_TABLE_NAME")
    fun getAll(): LiveData<List<Movie>>

    @Query("Select * FROM $MOVIE_TABLE_NAME WHERE id = :movieId")
    fun get(movieId : String): LiveData<Movie>

    @Query("Select * FROM $MOVIE_TABLE_NAME WHERE id = :movieId")
    fun getAsync(movieId: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)

}