package com.kreactive.test.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kreactive.test.app.data.model.local.MOVIE_TABLE_NAME
import com.kreactive.test.app.data.model.local.Movie
import com.kreactive.test.app.data.model.local.SEARCH_TABLE_NAME
import com.kreactive.test.app.data.model.local.Search

@Dao
interface SearchDao{

    @Query("Select * FROM $SEARCH_TABLE_NAME")
    fun getAll(): LiveData<List<Search>>

    @Query("Select * FROM $SEARCH_TABLE_NAME WHERE search = :search")
    fun get(search : String): LiveData<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(search: Search)

    @Delete
    fun delete(movie: Movie)

}