package com.kreactive.test.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kreactive.test.app.data.model.local.*

@Dao
interface SearchMovieJoinDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(join: SearchMovieJoin)

    @Query("Select * FROM $MOVIE_TABLE_NAME INNER JOIN $SEARCH_MOVIE_JOIN_TABLE_NAME ON $MOVIE_TABLE_NAME.id = $SEARCH_MOVIE_JOIN_TABLE_NAME.movieId WHERE $SEARCH_MOVIE_JOIN_TABLE_NAME.search = :search")
    fun getMoviesForSearch(search : String): LiveData<List<Movie>>

    @Query("Delete FROM $SEARCH_MOVIE_JOIN_TABLE_NAME WHERE $SEARCH_MOVIE_JOIN_TABLE_NAME.search = :search")
    fun delete(search : String)
}