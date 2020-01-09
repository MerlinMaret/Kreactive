package com.kreactive.test.app.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey


const val SEARCH_TABLE_NAME = "Search"
@Entity(tableName = SEARCH_TABLE_NAME)
class Search(
    @PrimaryKey
    val search : String,
    val totalResult : Int,
    val maxPageLoaded : Int
)